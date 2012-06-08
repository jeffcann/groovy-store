package au.com.dius.shop.rules

import au.com.dius.shop.Product

class FreeItemPerQtyPricingRule implements PricingRule
{
    Product product
    int qty

    FreeItemPerQtyPricingRule(product, qty)
    {
        this.product = product
        this.qty = qty
    }

    Double apply(items)
    {
        int count = 0
        items.each{ item -> count += (item.sku == product.sku ? 1 : 0) }

        int freeItems = count / qty
        return freeItems * product.price
    }
}
