package auth.auth_ui_components;

import auth.Callback;

/**
 * Represents the icons on the Tools panel.
 *
 * @author Anshu Dwibhashi
 */
public class ToolIcon extends Icon{
    @Override
    public void select() {

    }

    @Override
    public void deselect() {

    }
        public ToolIcon(String iconID, String tooltipText, Callback onClickCallback) {
        super(iconID, tooltipText, onClickCallback);
        super.setSelectable(false);
    }
    public ToolIcon(String iconID, String tooltipText, Callback onClickCallback, boolean selectable) {
        super(iconID, tooltipText, onClickCallback);
        super.setSelectable(selectable);
    }
}
