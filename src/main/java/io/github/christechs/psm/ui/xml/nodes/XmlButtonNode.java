package io.github.christechs.psm.ui.xml.nodes;

import io.github.christechs.psm.ui.components.ClayComponents;
import io.github.christechs.psm.ui.xml.XmlContext;
import org.w3c.dom.Element;

public class XmlButtonNode extends XmlNode {
    String text, onClick;

    public XmlButtonNode(Element el) {
        super(el);
        text = el.getAttribute("text");
        onClick = el.getAttribute("onClick");
    }

    @Override
    public void render(XmlContext ctx) {
        if (!shouldRender(ctx)) return;
        ClayComponents.button(ctx.resolveString(id), ctx.resolveString(text), ctx.getCallback(onClick));
    }
}