/*
 * The MIT License
 *
 *  Copyright (c) 2021, Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in
 *  all copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *  THE SOFTWARE.
 */
package org.jeasy.rules.tutorials.shop;

import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rule;
import org.jeasy.rules.api.Rules;
import org.jeasy.rules.api.RulesEngine;
import org.jeasy.rules.core.DefaultRulesEngine;
import org.jeasy.rules.jexl.JexlRule;
import org.jeasy.rules.jexl.JexlRuleFactory;
import org.jeasy.rules.mvel.MVELRule;
import org.jeasy.rules.mvel.MVELRuleFactory;
import org.jeasy.rules.spel.SpELRule;
import org.jeasy.rules.spel.SpELRuleFactory;
import org.jeasy.rules.support.reader.YamlRuleDefinitionReader;

import java.io.FileReader;
import java.sql.Time;
import java.util.Date;

public class Launcher {

    public static void main(String[] args) throws Exception {
        //create a person instance (fact)
        Person tom = new Person("Tom", 19);
        Parent jerry = new Parent(30, false);
        Facts facts = new Facts();
        facts.put("person", tom);
        facts.put("parent", jerry);

        String fileName = args.length != 0 ? args[0] : "easy-rules-tutorials/src/main/java/org/jeasy/rules/tutorials/shop/alcohol-rule.yml";

        //create a default rules engine and fire rules on known facts
        RulesEngine rulesEngine = new DefaultRulesEngine();

        // create rules
        MVELRule mvelRule = new MVELRule()
                .name("age rule")
                .description("Check if person's age is > 18 and mark the person as adult")
                .priority(1)
                .when("person.age > 18")
                .then("person.setAdult(true);");

        MVELRuleFactory mvelRuleFactory = new MVELRuleFactory(new YamlRuleDefinitionReader());
        Rule alcoholRule = mvelRuleFactory.createRule(new FileReader(fileName));

        // create a rule set
        Rules rules = new Rules();
        rules.register(mvelRule);
        rules.register(alcoholRule);


        System.out.println("Tom: Hi! can I have some Vodka please?");
        long start1 = System.nanoTime();
        rulesEngine.fire(rules, facts);
        long end1 = System.nanoTime();
        System.out.println("Elapsed Time in nano seconds MVEL: "+ (end1-start1));
        /////////////////////////////////////////////////

        RulesEngine spelRulesEngine = new DefaultRulesEngine();
        SpELRule spELRule = new SpELRule()
                .name("age rule using spel")
                .description("Check if person's age is > 18 and mark the person as adult")
                .priority(1)
                .when("#{ ['person'].age > 18 }")
                .then("#{ ['person'].setAdult(true) }");

        SpELRuleFactory spELRuleFactory = new SpELRuleFactory(new YamlRuleDefinitionReader());
        String spelFileName = args.length != 0 ? args[0] : "easy-rules-tutorials/src/main/java/org/jeasy/rules/tutorials/shop/alcohol-rule-spel.yml";
        Rule spelAlcoholRule = spELRuleFactory.createRule(new FileReader(spelFileName));

        Rules spelRules = new Rules();
        spelRules.register(spELRule);
        spelRules.register(spelAlcoholRule);
        long start2 = System.nanoTime();
        spelRulesEngine.fire(spelRules, facts);
        long end2 = System.nanoTime();
        System.out.println("Elapsed Time in nano seconds: SPEL "+ (end2-start2));

        /////////////////////////////////////////////////////////////////////////

        RulesEngine jexlRulesEngine = new DefaultRulesEngine();

        JexlRule jexlRule = new JexlRule()
                .name("age rule jexl")
                .description("Check if person's age is > 18 and mark the person as adult")
                .priority(1)
                .when("person.age > 18")
                .then("person.setAdult(true);");

        JexlRuleFactory jexlRuleFactory = new JexlRuleFactory(new YamlRuleDefinitionReader());
        String jexlFileName = args.length != 0 ? args[0] : "easy-rules-tutorials/src/main/java/org/jeasy/rules/tutorials/shop/alcohol-rule-jexl.yml";
        Rule jexlAlcoholRule = jexlRuleFactory.createRule(new FileReader(jexlFileName));
        Rules jexlRules = new Rules();
        jexlRules.register(jexlRule);
        jexlRules.register(jexlAlcoholRule);
        long start3 = System.nanoTime();
        jexlRulesEngine.fire(jexlRules, facts);
        long end3 = System.nanoTime();
        System.out.println("Elapsed Time in nano seconds: JEXL "+ (end3-start3));

    }

}
