package io.github.christechs.psm.ui.xml.nodes;

import io.github.christechs.psm.ui.xml.XmlContext;
import org.w3c.dom.Element;

public class XmlCustomNode extends XmlNode {

    public XmlCustomNode(Element el) {
        super(el);
    }

    @Override
    public void render(XmlContext ctx) {
        if (!shouldRender(ctx)) return;
        String resolvedId = ctx.resolveString(id);
        Runnable r = ctx.getCustomRenderer(resolvedId);
        if (r != null) r.run();
    }
}