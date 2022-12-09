package ch.srg.sam.detekt

import io.gitlab.arturbosch.detekt.api.*
import org.jetbrains.kotlin.psi.*

/**
 * Report any Schema annotation usage on a primary constructor parameter that has no use-site target.
 */
class SchemaAnnotationUseSiteTargetMissing(config: Config) : Rule(config) {

    override val issue = Issue(
        javaClass.simpleName,
        Severity.Defect,
        "Declared @Schema annotation has no effect.",
        Debt(mins = 2)
    )

    private var inCtor = false

    override fun visitPrimaryConstructor(constructor: KtPrimaryConstructor) {
        inCtor = true
        super.visitPrimaryConstructor(constructor)
        inCtor = false
    }

    override fun visitParameter(parameter: KtParameter) {
        if (inCtor) {
            parameter.annotationEntries
                .filter { entry -> entry.typeReference?.let { r -> r.text == "Schema" } ?: false }
                .filter { schemaEntry -> schemaEntry.useSiteTarget == null }
                .forEach {
                    report(
                        CodeSmell(
                            issue,
                            entity = Entity.from(it),
                            message = "Prefix @Schema annotation with a use-site target, e.g. get:@Schema(\"...\")"
                        )
                    )
                }
        }
        super.visitParameter(parameter)
    }
}
