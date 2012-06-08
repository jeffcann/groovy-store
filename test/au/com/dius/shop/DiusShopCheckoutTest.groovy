package au.com.dius.shop

import au.com.dius.shop.rules.FreeItemWithOtherItemPricingRule
import au.com.dius.shop.rules.FreeItemPerQtyPricingRule

import au.com.dius.shop.rules.BulkBuyDiscountPricingRule

class DiusShopCheckoutTest extends GroovyTestCase
{
    void testCreatingCheckout()
    {
        DiusShopCheckout checkout = new DiusShopCheckout([])
        assert checkout.pricingRules != null
    }

    void testFreeItemWithOtherItem()
    {
        Catalogue catalogue = new Catalogue("products.psv")
        DiusShopCheckout checkout = new DiusShopCheckout([new FreeItemWithOtherItemPricingRule(catalogue.findProduct("mbp"), catalogue.findProduct("vga"))])
        checkout.catalogue = catalogue

        checkout.scan("vga")
        assert checkout.total() == 30.00

        checkout.scan("mbp")
        assert checkout.total() == 1399.99

        checkout.scan("mbp")
        assert checkout.total() == 2799.98

        checkout.scan("vga")
        assert checkout.total() == 2799.98
    }

    void test3for2DealApplied()
    {
        Catalogue catalogue = new Catalogue("products.psv")
        DiusShopCheckout checkout = new DiusShopCheckout([new FreeItemPerQtyPricingRule(catalogue.findProduct("atv"), 3)])
        checkout.catalogue = catalogue

        checkout.scan("atv")
        checkout.scan("atv")
        assert checkout.total() == (109.50 * 2)

        checkout.scan("atv")
        assert checkout.total() == (109.50 * 2)

        checkout.scan("atv")
        assert checkout.total() == (109.50 * 3)

        checkout.scan("atv")
        assert checkout.total() == (109.50 * 4)

        checkout.scan("atv")
        assert checkout.total() == (109.50 * 4)
    }

    void testBulkBuyDiscountApplied()
    {
        Catalogue catalogue = new Catalogue("products.psv")
        DiusShopCheckout checkout = new DiusShopCheckout([new BulkBuyDiscountPricingRule(catalogue.findProduct("ipd"), 5, 50.0)])
        checkout.catalogue = catalogue

        4.times{ checkout.scan("ipd") }
        assert checkout.total() == (549.99 * 4)

        checkout.scan("ipd")
        assert checkout.total() == (499.99 * 5)
    }

    void testCheckingTotalWithoutRules()
    {
        DiusShopCheckout checkout = new DiusShopCheckout([])
        checkout.catalogue = new Catalogue("products.psv")
        assert checkout.total() == 0.0

        checkout.scan("mbp")
        assert checkout.total() == 1399.99

        checkout.scan("atv")
        assert checkout.total() == (1399.99 + 109.50)

        checkout.scan("atv")
        assert checkout.total() == (1399.99 + 109.50 + 109.50)
    }

    void testScanningAnInvalidItem()
    {
        DiusShopCheckout checkout = new DiusShopCheckout([])
        checkout.catalogue = new Catalogue("products.psv")

        shouldFail{ checkout.scan("xyz") }
    }

    void testScanningAnItem()
    {
        DiusShopCheckout checkout = new DiusShopCheckout([])
        checkout.catalogue = new Catalogue("products.psv")
        assert checkout.size() == 0

        checkout.scan("mbp")
        assert checkout.size() == 1
        assert checkout.items.collect{it.sku}.contains("mbp")

        checkout.scan("atv")
        assert checkout.size() == 2
        assert checkout.items.collect{it.sku}.contains("mbp")
        assert checkout.items.collect{it.sku}.contains("atv")
    }
}
