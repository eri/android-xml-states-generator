package com.erioxyde.app.asg;

import javax.swing.UIManager;

import com.erioxyde.app.asg.ui.DropWindow;

public class AndroidStateListGenerator
{

  public static void main(String[] args)
  {
    // Try to use native look'n'feel UI.
    try
    {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    }
    catch (Exception exception)
    {
      exception.printStackTrace();
    }
    final DropWindow dropWindow = new DropWindow();
    dropWindow.show();
  }

}