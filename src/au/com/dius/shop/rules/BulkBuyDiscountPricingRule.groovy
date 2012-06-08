package au.com.dius.shop.rules

import au.com.dius.shop.Product

class BulkBuyDiscountPricingRule implements PricingRule
{
    Product product
    int qtyBreak
    double discount

    BulkBuyDiscountPricingRule(product, qtyBreak, discount)
    {
        this.product = product
        this.qtyBreak = qtyBreak
        this.discount = discount
    }

    Double apply(items)
    {
        def qty = 0
        items.each{ item -> qty += (item.sku == product.sku ? 1 : 0) }
        return (qty >= qtyBreak) ? qty * discount : 0.0
    }
}
