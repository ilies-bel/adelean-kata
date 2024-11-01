package com.adelean.Reader.models;

import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.util.ArrayList;
import java.util.List;

public record Product(
        String title,
        String titleShort,
        String sku,
        Brand brand,
        String category,
        String ean13,
        Description description,
        List<String> media,
        Warranty warranty,
        String color,
        String weightG,

        Dimension dimension,

        String chargeConsumption,
        SmartphoneSpec smartphoneSpec

) {

    public static Product fromXml(XMLStreamReader reader) throws XMLStreamException {
        Builder builder = new Builder();
        builder.parseFrom(reader);
        return builder.build();
    }

    public static class Builder {
        private final List<String> media = new ArrayList<>();
        private final SmartphoneSpec smartphoneSpec = new SmartphoneSpec();
        private String titleFull;
        private String titleShort;
        private String sku;
        private String brand;
        private String brandReference;
        private String category;
        private String warranty;
        private String color;
        private String weightG;
        private String warrantyScope;
        private String ean13;
        private String descriptionFull;
        private String descriptionShort;
        private String dimensionLength;
        private String dimensionHeight;
        private String chargeConsumption;

        public void parseFrom(XMLStreamReader reader) throws XMLStreamException {
            while (reader.hasNext()) {
                int event = reader.next();
                if (event == XMLStreamConstants.START_ELEMENT) {
                    String elementName = reader.getLocalName();

                    switch (elementName) {
                        case "article_sku" -> sku = XmlParserUtils.readCharacters(reader);

                        case "brand" -> brand = XmlParserUtils.readCharacters(reader);
                        case "brand_reference" -> brandReference = XmlParserUtils.readCharacters(reader);

                        case "category" -> category = XmlParserUtils.readCharacters(reader);

                        case "long_title" -> titleFull = XmlParserUtils.readCharacters(reader);
                        case "short_title" -> titleShort = XmlParserUtils.readCharacters(reader);

                        case "ean13" -> ean13 = XmlParserUtils.readCharacters(reader);

                        case "full_description" -> descriptionFull = XmlParserUtils.readCharacters(reader);
                        case "short_description" -> descriptionShort = XmlParserUtils.readCharacters(reader);

                        case "warranty" -> warranty = XmlParserUtils.readCharacters(reader);
                        case "warranty_scope" -> warrantyScope = XmlParserUtils.readCharacters(reader);

                        case "dimensions_length_cm" -> dimensionLength = XmlParserUtils.readCharacters(reader);
                        case "dimensions_height_cm" -> dimensionHeight = XmlParserUtils.readCharacters(reader);
                        case "charge_consumption_watts" -> chargeConsumption = XmlParserUtils.readCharacters(reader);

                        case "color" -> color = XmlParserUtils.readCharacters(reader);
                        case "weight_g" -> weightG = XmlParserUtils.readCharacters(reader);
                    }

                    if (elementName.startsWith("media_url_")) {
                        media.add(XmlParserUtils.readCharacters(reader));
                    }

                    if (elementName.startsWith("it_")) {
                        smartphoneSpec.add(elementName, XmlParserUtils.readCharacters(reader));
                    }

                } else if (event == XMLStreamConstants.END_ELEMENT && "product".equals(reader.getLocalName())) {
                    break;
                }
            }
        }

        public Product build() {

            return new Product(titleFull,
                    titleShort,
                    sku,
                    new Brand(brand, brandReference),
                    category,
                    ean13,
                    new Description(descriptionFull, descriptionShort),
                    media,
                    new Warranty(warranty, warrantyScope),
                    color,
                    weightG,
                    new Dimension(
                            dimensionLength,
                            dimensionHeight
                    ),
                    chargeConsumption,
                    smartphoneSpec);
        }
    }
}

