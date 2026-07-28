package io.github.christechs.psm.ui.xml.nodes;

import io.github.christechs.psm.ui.components.ClayComponents;
import io.github.christechs.psm.ui.xml.XmlContext;
import org.w3c.dom.Element;

public class XmlInputNode extends XmlNode {
    String placeholder, bind, widthStr;

    public XmlInputNode(Element el) {
        super(el);
        placeholder = el.getAttribute("placeholder");
        bind = el.getAttribute("bind");
        widthStr = el.getAttribute("width");
    }

    @Override
    public void render(XmlContext ctx) {
        if (!shouldRender(ctx)) return;

        float width = -1;
        String rw = ctx.resolveString(widthStr);
        if (rw != null && !rw.isEmpty()) {
            try {
                width = Float.parseFloat(rw);
            } catch (Exception ignored) {
            }
        }

        String varName = bind.replace("${", "").replace("}", "");

        ClayComponents.textInput(
                ctx.resolveString(id),
                ctx.resolveString(placeholder),
                ctx.resolveString("${" + varName + "}"),
                width,
                ctx.getStringSetter(varName));
    }
}