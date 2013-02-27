package com.erioxyde.app.asg.bo;

import java.io.File;

import com.erioxyde.app.asg.Constants;
import com.erioxyde.app.asg.Constants.StateList;

public final class StateAttributes
{
  public File file;

  public final String baseName;

  public StateList state;

  public Boolean isEnabled = null;

  private Boolean isDefault = null;

  private Boolean isPressed = null;

  private Boolean isFocused = null;

  private Boolean isSelected = null;

  private Boolean isHovered = null;

  private Boolean isChecked = null;

  private Boolean isCheckable = null;

  private Boolean isActivated = null;

  private Boolean isWindowFocused = null;

  public StateAttributes(File file)
  {
    this.file = file;
    this.baseName = Constants.getCleanFileName(file);
    for (StateList stateList : StateList.values())
    {
      if (baseName.endsWith(stateList.toString()) == true)
      {
        this.state = stateList;
        switch (stateList)
        {
        case _default:
          isDefault = true;
          break;
        case pressed:
        case clicked:
        case press:
        case click:
          isPressed = true;
          break;
        case selected:
        case select:
          isSelected = true;
          break;
        case activated:
        case active:
          isActivated = true;
          break;
        case focused:
        case focus:
          isFocused = true;
          break;
        case disabled:
        case disable:
          isEnabled = false;
          break;
        case enabled:
        case enable:
          isEnabled = true;
          break;
        case hovered:
        case hover:
          isHovered = true;
          break;
        case checked:
        case check:
          isChecked = true;
          break;
        case checkable:
          isCheckable = true;
          break;
        case window_focused:
          isWindowFocused = true;
          break;
        default:
          break;
        }
      }
      if (baseName.contains(StateList.disabled.toString()) == true)
      {
        isEnabled = false;
      }
    }
  }

  public boolean isDefault()
  {
    return isDefault != null && isDefault == true;
  }

  public boolean isPressed()
  {
    return isPressed != null && isPressed == true;
  }

  public boolean isFocused()
  {
    return isFocused != null && isFocused == true;
  }

  public boolean isSelected()
  {
    return isSelected != null && isSelected == true;
  }

  public boolean isEnabled()
  {
    return isEnabled != null && isEnabled == true;
  }

  public boolean isHovered()
  {
    return isHovered != null && isHovered == true;
  }

  public boolean isChecked()
  {
    return isChecked != null && isChecked == true;
  }

  public boolean isCheckable()
  {
    return isCheckable != null && isCheckable == true;
  }

  public boolean isActivated()
  {
    return isActivated != null && isActivated == true;
  }

  public boolean isWindowFocused()
  {
    return isWindowFocused != null && isWindowFocused == true;
  }

}
