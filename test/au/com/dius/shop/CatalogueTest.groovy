package au.com.dius.shop

class CatalogueTest extends GroovyTestCase
{
    void testReadingInProducts()
    {
        Catalogue catalogue = new Catalogue("products.psv")
        assert catalogue.size() == 4
    }

    void testFindingProduct()
    {
        Catalogue catalogue = new Catalogue("products.psv")

        Product product = catalogue.findProduct("mbp")

        assert product != null
        assert product.sku == "mbp"
        assert product.name == "MacBook Pro"
        assert product.price == 1399.99
    }
}
