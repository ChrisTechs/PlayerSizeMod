package io.github.christechs.psm.ui.xml.nodes;

import io.github.christechs.clayj.config.TextConfigBuilder;
import io.github.christechs.psm.ui.xml.XmlContext;
import org.w3c.dom.Element;

import static io.github.christechs.clayj.ClayJ.text;
import static io.github.christechs.clayj.ClayJ.txt;

public class XmlTextNode extends XmlNode {
    String text, color, size;

    public XmlTextNode(Element el) {
        super(el);
        text = el.getAttribute("text");
        color = el.getAttribute("color");
        size = el.getAttribute("size");
    }

    @Override
    public void render(XmlContext ctx) {
        if (!shouldRender(ctx)) return;

        TextConfigBuilder t = txt();
        String rSize = ctx.resolveString(size);
        if (rSize != null && !rSize.isEmpty()) {
            try {
                t.size(Integer.parseInt(rSize));
            } catch (Exception ignored) {
            }
        }
        String rColor = ctx.resolveString(color);
        if (rColor != null && !rColor.isEmpty()) applyTextColor(t, rColor);
        text(ctx.resolveString(text), t);
    }
}