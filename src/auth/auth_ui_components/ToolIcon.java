package auth.auth_ui_components;

import auth.Callback;

public class ToolIcon extends Icon{
    public ToolIcon(String iconID, String tooltipText, Callback onClickCallback) {
        super(iconID, tooltipText, onClickCallback);
        super.setSelectable(false);
    }
    public ToolIcon(String iconID, String tooltipText, Callback onClickCallback, boolean selectable) {
        super(iconID, tooltipText, onClickCallback);
        super.setSelectable(selectable);
    }
}
