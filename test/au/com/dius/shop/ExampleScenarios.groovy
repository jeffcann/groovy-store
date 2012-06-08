package au.com.dius.shop

import au.com.dius.shop.rules.FreeItemPerQtyPricingRule
import au.com.dius.shop.rules.FreeItemWithOtherItemPricingRule
import au.com.dius.shop.rules.BulkBuyDiscountPricingRule

class ExampleScenarios extends GroovyTestCase {

    DiusShopCheckout checkout

    void setUp() {

        Catalogue catalogue = new Catalogue("products.psv")

        checkout = new DiusShopCheckout([
                new FreeItemPerQtyPricingRule(catalogue.findProduct("atv"), 3),
                new BulkBuyDiscountPricingRule(catalogue.findProduct("ipd"), 5, 50.0),
                new FreeItemWithOtherItemPricingRule(catalogue.findProduct("mbp"), catalogue.findProduct("vga"))
        ])

        checkout.catalogue = catalogue
    }

    void testScenario1() {
        runScenario("atv, atv, atv, vga", 249.00)
    }

    void testScenario2() {
        runScenario("mbp, vga, ipd", 1949.98)
    }

    void testScenario3() {
        runScenario("atv, ipd, ipd, atv, ipd, ipd, ipd", 2718.95)
    }

    void runScenario(items, expectedPrice) {
        items.split(", ").each { sku -> checkout.scan(sku) }
        assert checkout.total() == expectedPrice
    }

}
