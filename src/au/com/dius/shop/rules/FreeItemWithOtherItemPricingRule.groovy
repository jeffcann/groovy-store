package au.com.dius.shop.rules

import au.com.dius.shop.Product

class FreeItemWithOtherItemPricingRule implements PricingRule
{
    Product paidProduct
    Product freeProduct

    FreeItemWithOtherItemPricingRule(paidProduct, freeProduct)
    {
        this.paidProduct = paidProduct
        this.freeProduct = freeProduct
    }

    Double apply(items)
    {
        int paidItemCount = 0
        items.each{ item -> paidItemCount += (item.sku == paidProduct.sku ? 1 : 0) }

        int freeItemCount = 0
        items.each{ item -> freeItemCount += (item.sku == freeProduct.sku ? 1 : 0) }

        return Math.min(freeItemCount, paidItemCount) * freeProduct.price
    }
}
