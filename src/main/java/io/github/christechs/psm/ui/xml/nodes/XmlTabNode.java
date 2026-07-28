package io.github.christechs.psm.ui.xml.nodes;

import io.github.christechs.psm.ui.components.ClayComponents;
import io.github.christechs.psm.ui.xml.XmlContext;
import org.w3c.dom.Element;

public class XmlTabNode extends XmlNode {
    String text, active, onClick;

    public XmlTabNode(Element el) {
        super(el);
        text = el.getAttribute("text");
        active = el.getAttribute("active");
        onClick = el.getAttribute("onClick");
    }

    @Override
    public void render(XmlContext ctx) {
        if (!shouldRender(ctx)) return;
        ClayComponents.tab(
                ctx.resolveString(id),
                ctx.resolveString(text),
                ctx.resolveBoolean(active),
                ctx.getCallback(onClick));
    }
}