package au.com.dius.shop

class DiusShopCheckout implements Checkout
{
    Catalogue catalogue
    def pricingRules = []
    def items = []

    DiusShopCheckout(pricingRules)
    {
        this.pricingRules = pricingRules
    }

    void scan(String sku)
    {
        def product = catalogue.findProduct(sku)

        if(product != null)
            items.add(product)
        else
            throw new RuntimeException("sku not found (${sku})")
    }

    Double total()
    {
        def subtotal = 0.0
        items.each{ product ->  subtotal += product.price }

        def discount = 0.0
        pricingRules.each{ rule -> discount += rule.apply(items) }

        return subtotal - discount
    }

    int size()
    {
        items.size()
    }
}
