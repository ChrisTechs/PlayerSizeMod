package io.github.christechs.psm.ui.xml.nodes;

import io.github.christechs.psm.ui.components.ClayComponents;
import io.github.christechs.psm.ui.xml.XmlContext;
import org.w3c.dom.Element;

public class XmlScrollbarNode extends XmlNode {
    String target;

    public XmlScrollbarNode(Element el) {
        super(el);
        target = el.getAttribute("target");
    }

    @Override
    public void render(XmlContext ctx) {
        if (!shouldRender(ctx)) return;
        ClayComponents.scrollbar(ctx.resolveString(target));
    }
}