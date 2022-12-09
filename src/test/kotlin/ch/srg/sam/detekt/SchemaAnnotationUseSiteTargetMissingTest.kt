package ch.srg.sam.detekt

import io.gitlab.arturbosch.detekt.api.Config
import io.gitlab.arturbosch.detekt.rules.KotlinCoreEnvironmentTest
import io.gitlab.arturbosch.detekt.test.compileAndLintWithContext
import io.kotest.matchers.collections.shouldHaveSize
import org.jetbrains.kotlin.cli.jvm.compiler.KotlinCoreEnvironment
import org.junit.jupiter.api.Test

data class AST(
    @get:Suppress
    val x: Any?
)

@KotlinCoreEnvironmentTest
internal class SchemaAnnotationUseSiteTargetMissingTest(private val env: KotlinCoreEnvironment) {

    @Test
    fun `reports primary constructor parameter`() {
        val code = """
class AST(
    @Schema
    val x: Any
)
        """
        val findings = SchemaAnnotationUseSiteTargetMissing(Config.empty).compileAndLintWithContext(env, code)
        findings shouldHaveSize 1
    }
}
