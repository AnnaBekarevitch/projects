#ifndef MAINWINDOW_H
#define MAINWINDOW_H

#include "first_function.h"
#include "plot.h"
#include "second_function.h"
#include <QMainWindow>
#include <QTranslator>
#include <QtGui/QPainter>
#include <QtGui/QScreen>
#include <QtWidgets/QApplication>
#include <QtWidgets/QCheckBox>
#include <QtWidgets/QComboBox>
#include <QtWidgets/QGroupBox>
#include <QtWidgets/QHBoxLayout>
#include <QtWidgets/QLabel>
#include <QtWidgets/QMessageBox>
#include <QtWidgets/QPushButton>
#include <QtWidgets/QRadioButton>
#include <QtWidgets/QSlider>
#include <QtWidgets/QVBoxLayout>
#include <QtWidgets/QWidget>

class QAction;

enum PlotFunction { First = 0, Second = 1 };

enum class Translation { Russian, English, Default };

class MainWindow : public QMainWindow {
public:
  explicit MainWindow();
  FirstFunction m_firstFunction;
  SecondFunction m_secondFunction;
public slots:
  void do_save();
  void do_revert();
  void do_lang_ru();
  void do_lang_en();

private:
  Q_OBJECT

  QWidget *m_widget;
  QTranslator m_qtLanguageTranslator;
  Plot *m_modifier;
  QAction *m_actSave, *m_actRevert, *m_actLangRu, *m_actLangEn;
  QSettings *m_settings;
  QCheckBox *m_show_grid, *m_show_label, *m_show_label_bordered;
  QRadioButton *m_sinc_dist_model_rb, *m_sinc_x_sinc_z_model_rb;
  QRadioButton *m_modeNoneRB, *m_modeItemRB;
  QPushButton *m_gradient_inferno, *m_gradient_plasma;
  QSlider *m_axisMinSliderX, *m_axisMaxSliderX, *m_axisMinSliderZ, *m_axisMaxSliderZ;
  QMenu *m_menu, *m_menu2;
  QToolBar *m_toolbar;
  QLabel *m_column_title, *m_row_title, *m_theme_title, *m_x_step_title, *m_z_step_title,
      *m_x_interval_title, *m_z_interval_title;
  QLineEdit *m_x_step, *m_z_step, *m_x_begin, *m_x_end, *m_z_begin, *m_z_end;
  QGroupBox *m_colorGroupBox, *m_selectionGroupBox, *m_modelGroupBox;
  Q3DSurface *m_surface;

  Translation m_current_translation = Translation::Default;
  void updateText();
};

#endif // MAINWINDOW_H
