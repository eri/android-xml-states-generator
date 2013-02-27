package com.erioxyde.app.asg.ui;

import javax.swing.JFrame;

public abstract class FrameWindow
{

  protected final JFrame window;

  public FrameWindow(String title, int width, int height)
  {
    window = new JFrame();
    window.setTitle(title);
    window.setSize(width, height);
    window.setLocationRelativeTo(null);
  }

  public void show()
  {
    show(true);
  }

  public void show(boolean show)
  {
    window.setVisible(show);
  }

}
