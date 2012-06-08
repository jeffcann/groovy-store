package au.com.dius.shop

class Catalogue
{
    def products = []

    Catalogue(filename)
    {
        def file = new File(filename)
        products = readProducts(file.getText())
    }

    List readProducts(String data)
    {
        def lines = data.split("\n")

        (lines - lines[0]).collect {
            def fields = it.split("\\|")
            new Product(fields[1].trim(), fields[2].trim(), fields[3].trim())
        }
    }

    int size()
    {
        return products.size()
    }

    Product findProduct(String sku)
    {
        products.find{ product -> product.sku == sku }
    }
}
