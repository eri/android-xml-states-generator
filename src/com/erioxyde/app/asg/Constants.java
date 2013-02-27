package com.erioxyde.app.asg;

import java.io.File;
import java.util.List;

import org.apache.commons.io.FilenameUtils;

import com.erioxyde.app.asg.bo.StateAttributes;

public final class Constants
{

  public enum StateList
  {
    _default, //
    window_focused, //
    pressed, clicked, press, click, // pressed states
    selected, select, // selected state
    checkable, // checkable state
    checked, check, // checked state
    focused, focus, // focused state
    hovered, hover, // hovered state
    activated, active, // activated state
    enabled, enable, disabled, disable // enabled state to false
  }

  public interface StateRessourceDropListener
  {
    public void onDrop(List<StateAttributes> ressources);
  }

  public static final String TRUE = Boolean.TRUE.toString();

  public static final String FALSE = Boolean.FALSE.toString();

  public static final String APP_TITLE = "Android StateList Generator";

  public static final String DRAWABLE_PREFIX = "@drawable/";

  public static final String XML_ANDROID_NAMESPACE = "android";

  public static final String DRAWABLE_EXTENSION = "png";

  public static final String XML_EXTENSION = "xml";

  public static final String DOT_XML_EXTENSION = ".xml";

  public static final String SAVE_AS_XML_DESCRIPTION = "Android XML Drawable File";

  public static String getCleanFileName(File file)
  {
    return Constants.remove9PatchExtension(FilenameUtils.getBaseName(file.getName().toLowerCase()));
  }

  public static String remove9PatchExtension(String string)
  {
    return string.replaceAll(".9", "");
  }

}
