package io.github.christechs.psm.ui.xml;

import io.github.christechs.psm.ui.xml.nodes.*;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.Identifier;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import static io.github.christechs.psm.PlayerSizeMod.PSM_LOGGER;

public class ClayXmlEngine {

    private static final Map<String, XmlNode> CACHE = new HashMap<>();

    public static XmlNode load(String resourcePath) {
        if (CACHE.containsKey(resourcePath)) return CACHE.get(resourcePath);
        try {
            Identifier xmlLoc = Identifier.fromNamespaceAndPath("psm", resourcePath);
            InputStream stream = Minecraft.getInstance().getResourceManager().getResource(xmlLoc).get().open();
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(stream);
            doc.getDocumentElement().normalize();
            XmlNode rootNode = parseNode(doc.getDocumentElement());
            CACHE.put(resourcePath, rootNode);
            return rootNode;
        } catch (Exception e) {
            PSM_LOGGER.error("Failed to load XML UI: {}", resourcePath);
            return null;
        }
    }

    private static XmlNode parseNode(Node domNode) {
        if (domNode.getNodeType() != Node.ELEMENT_NODE) return null;
        Element el = (Element) domNode;
        String tag = el.getTagName();

        XmlNode node = createNodeInstance(tag, el);
        if (node != null) {
            NodeList children = el.getChildNodes();
            for (int i = 0; i < children.getLength(); i++) {
                XmlNode child = parseNode(children.item(i));
                if (child != null) node.children.add(child);
            }
        }
        return node;
    }

    private static XmlNode createNodeInstance(String tag, Element el) {
        switch (tag) {
            case "Screen":
            case "Element":
                return new XmlLayoutNode(el);
            case "Text":
                return new XmlTextNode(el);
            case "Button":
                return new XmlButtonNode(el);
            case "Tab":
                return new XmlTabNode(el);
            case "Custom":
                return new XmlCustomNode(el);
            case "Input":
                return new XmlInputNode(el);
            case "Scrollbar":
                return new XmlScrollbarNode(el);
            default:
                return null;
        }
    }
}