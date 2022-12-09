package ch.srg.sam.detekt

import io.gitlab.arturbosch.detekt.api.Config
import io.gitlab.arturbosch.detekt.api.RuleSet
import io.gitlab.arturbosch.detekt.api.RuleSetProvider

class SamRulesProvider : RuleSetProvider {
    override val ruleSetId: String = "SamRules"

    override fun instance(config: Config): RuleSet {
        return RuleSet(
            ruleSetId,
            listOf(
                SchemaAnnotationUseSiteTargetMissing(config),
            ),
        )
    }
}
