
#include "mainwindow.h"
#include "plot.h"
#include <QApplication>
#include <QDir>
#include <QFileInfo>
#include <QLibraryInfo>
#include <QLineEdit>
#include <QMenuBar>
#include <QStatusBar>
#include <QToolBar>
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
#include <iostream>

MainWindow::MainWindow() : QMainWindow() {
  if (m_qtLanguageTranslator.load(QString("Translation_") +
                                    QLocale::system().name(),
                                ":/translations")) {
    qApp->installTranslator(&m_qtLanguageTranslator);
  }

  QSettings::setDefaultFormat(QSettings::IniFormat);
  QFileInfo settings_file(QDir(QCoreApplication::applicationDirPath()),
                          QString("settings.ini"));
  m_settings =
      new QSettings(settings_file.absoluteFilePath(), QSettings::IniFormat);

  m_widget = new QWidget;
  m_surface = new Q3DSurface();
  m_modifier = new Plot(m_surface, {&m_firstFunction, &m_secondFunction});
  QWidget *container = QWidget::createWindowContainer(m_surface);
  QSize screenSize = m_surface->screen()->size();
  container->setMinimumSize(
      QSize(screenSize.width() / 2, screenSize.height() / 1.6));
  container->setMaximumSize(screenSize);
  container->setSizePolicy(QSizePolicy::Expanding, QSizePolicy::Expanding);
  container->setFocusPolicy(Qt::StrongFocus);

  QHBoxLayout *hLayout = new QHBoxLayout(m_widget);
  QVBoxLayout *vLayout = new QVBoxLayout();
  hLayout->addWidget(container, 1);
  hLayout->addLayout(vLayout);
  vLayout->setAlignment(Qt::AlignTop);

  QGroupBox *checkGroupBox = new QGroupBox();
  QVBoxLayout *modelCBox = new QVBoxLayout;
  m_show_grid = new QCheckBox();
  m_show_label = new QCheckBox();
  m_show_label_bordered = new QCheckBox();
  modelCBox->addWidget(m_show_grid);
  modelCBox->addWidget(m_show_label);
  modelCBox->addWidget(m_show_label_bordered);
  checkGroupBox->setLayout(modelCBox);
  m_modelGroupBox = new QGroupBox();

  m_sinc_dist_model_rb = new QRadioButton(m_widget);
  m_sinc_dist_model_rb->setChecked(false);

  m_sinc_x_sinc_z_model_rb = new QRadioButton(m_widget);
  m_sinc_x_sinc_z_model_rb->setChecked(false);

  QVBoxLayout *modelVBox = new QVBoxLayout;
  modelVBox->addWidget(m_sinc_dist_model_rb);
  modelVBox->addWidget(m_sinc_x_sinc_z_model_rb);
  m_modelGroupBox->setLayout(modelVBox);

  m_selectionGroupBox = new QGroupBox();

  m_modeNoneRB = new QRadioButton(m_widget);
  m_modeNoneRB->setChecked(false);

  m_modeItemRB = new QRadioButton(m_widget);
  m_modeItemRB->setChecked(false);

  QVBoxLayout *selectionVBox = new QVBoxLayout;
  selectionVBox->addWidget(m_modeNoneRB);
  selectionVBox->addWidget(m_modeItemRB);

  m_selectionGroupBox->setLayout(selectionVBox);

  m_x_step_title = new QLabel();
  m_x_interval_title = new QLabel();

  m_z_step_title = new QLabel();
  m_z_interval_title = new QLabel();

  QWidget *x_interval_GroupBox = new QWidget();
  QHBoxLayout *x_layout = new QHBoxLayout();
  m_x_begin = new QLineEdit();
  m_x_begin->setText(QString::number(m_modifier->m_min_x));
  m_x_begin->setValidator(new QDoubleValidator());
  m_x_end = new QLineEdit();
  m_x_end->setText(QString::number(m_modifier->m_max_x));
  x_layout->addWidget(m_x_begin);
  x_layout->addWidget(m_x_end);
  x_interval_GroupBox->setLayout(x_layout);
  m_x_step = new QLineEdit();
  m_x_step->setText(QString::number(m_modifier->m_count_x));
  m_x_step->setValidator(new QIntValidator());

  QObject::connect(m_x_begin, &QLineEdit::textChanged, this, [this](QString val) {
    double left = val.toDouble();
    double right = m_x_end->text().toDouble();
    if (left >= right) {
      left = right - 0.1;
      m_x_begin->setText(QString::number(left));
    }
    m_modifier->changeIntervalX(left, right);
    m_modifier->setAxisXRange(m_axisMinSliderX->value(), m_axisMaxSliderX->value());
  });
  QObject::connect(m_x_end, &QLineEdit::textChanged, this, [this](QString val) {
    double left = m_x_begin->text().toDouble();
    double right = val.toDouble();
    if (left >= right) {
      right = left + 0.1;
      m_x_end->setText(QString::number(right));
    }

    m_modifier->changeIntervalX(left, right);
    m_modifier->setAxisXRange(m_axisMinSliderX->value(), m_axisMaxSliderX->value());
  });
  QObject::connect(m_x_step, &QLineEdit::textChanged, this, [this](QString val) {
    int step = val.toInt();
    if (step > 0) {
      m_modifier->changeStepCountX(step);
      m_axisMinSliderX->setMaximum(m_modifier->m_count_x - 2);
      m_axisMaxSliderX->setMaximum(m_modifier->m_count_x - 1);
      m_modifier->setAxisXRange(m_axisMinSliderX->value(), m_axisMaxSliderX->value());
    }
  });

  QHBoxLayout *z_layout = new QHBoxLayout();
  QWidget *z_interval_GroupBox = new QWidget();
  m_z_begin = new QLineEdit();
  m_z_begin->setText(QString::number(m_modifier->m_min_z));
  m_z_end = new QLineEdit();
  m_z_end->setText(QString::number(m_modifier->m_max_z));
  z_layout->addWidget(m_z_begin);
  z_layout->addWidget(m_z_end);
  z_interval_GroupBox->setLayout(z_layout);
  m_z_step = new QLineEdit();
  m_z_step->setValidator(new QIntValidator());
  m_z_step->setText(QString::number(m_modifier->m_count_z));

  QObject::connect(m_z_begin, &QLineEdit::textChanged, this, [this](QString val) {
    double left = val.toDouble();
    double right = m_z_end->text().toDouble();
    if (left >= right) {
      left = right - 0.1;
      m_z_begin->setText(QString::number(left));
    }
    m_modifier->changeIntervalZ(left, right);
    m_modifier->setAxisZRange(m_axisMinSliderZ->value(), m_axisMaxSliderZ->value());
  });
  QObject::connect(m_z_end, &QLineEdit::textChanged, this, [this](QString val) {
    double left = m_z_begin->text().toDouble();
    double right = val.toDouble();
    if (left >= right) {
      right = left + 0.1;
      m_z_end->setText(QString::number(right));
    }

    m_modifier->changeIntervalZ(left, right);
    m_modifier->setAxisZRange(m_axisMinSliderZ->value(), m_axisMaxSliderZ->value());
  });
  QObject::connect(m_z_step, &QLineEdit::textChanged, this, [this](QString val) {
    int step = val.toInt();
    if (step > 0) {
      m_modifier->changeStepCountZ(step);
      m_axisMinSliderZ->setMaximum(m_modifier->m_count_z - 2);
      m_axisMaxSliderZ->setMaximum(m_modifier->m_count_z - 1);
      m_modifier->setAxisZRange(m_axisMinSliderZ->value(), m_axisMaxSliderZ->value());
    }
  });

  m_axisMinSliderX = new QSlider(Qt::Horizontal, m_widget);
  m_axisMinSliderX->setMinimum(0);
  m_axisMinSliderX->setTickInterval(1);
  m_axisMaxSliderX = new QSlider(Qt::Horizontal, m_widget);
  m_axisMaxSliderX->setMinimum(1);
  m_axisMaxSliderX->setTickInterval(1);
  m_axisMinSliderZ = new QSlider(Qt::Horizontal, m_widget);
  m_axisMinSliderZ->setMinimum(0);
  m_axisMinSliderZ->setTickInterval(1);
  m_axisMaxSliderZ = new QSlider(Qt::Horizontal, m_widget);
  m_axisMaxSliderZ->setMinimum(1);
  m_axisMaxSliderZ->setTickInterval(1);

  m_axisMinSliderX->setMaximum(m_modifier->m_count_x - 2);
  m_axisMinSliderX->setValue(0);
  m_axisMaxSliderX->setMaximum(m_modifier->m_count_x - 1);
  m_axisMaxSliderX->setValue(m_modifier->m_count_x - 1);
  m_axisMinSliderZ->setMaximum(m_modifier->m_count_z - 2);
  m_axisMinSliderZ->setValue(0);
  m_axisMaxSliderZ->setMaximum(m_modifier->m_count_z - 1);
  m_axisMaxSliderZ->setValue(m_modifier->m_count_z - 1);

  m_colorGroupBox = new QGroupBox();

  QPixmap pm(24, 100);
  QPainter pmp(&pm);
  pmp.setBrush(QBrush(m_modifier->m_inferno_colors));
  pmp.setPen(Qt::NoPen);
  pmp.drawRect(0, 0, 24, 100);
  m_gradient_inferno = new QPushButton(m_widget);
  m_gradient_inferno->setIcon(QIcon(pm));
  m_gradient_inferno->setIconSize(QSize(24, 100));

  pmp.setBrush(QBrush(m_modifier->m_plasma_colors));
  pmp.drawRect(0, 0, 24, 100);
  m_gradient_plasma = new QPushButton(m_widget);
  m_gradient_plasma->setIcon(QIcon(pm));
  m_gradient_plasma->setIconSize(QSize(24, 100));

  QHBoxLayout *colorHBox = new QHBoxLayout;
  colorHBox->addWidget(m_gradient_inferno);
  colorHBox->addWidget(m_gradient_plasma);
  m_colorGroupBox->setLayout(colorHBox);

  m_column_title = new QLabel();
  m_row_title = new QLabel();
  m_theme_title = new QLabel();

  vLayout->addWidget(checkGroupBox);
  vLayout->addWidget(m_modelGroupBox);
  vLayout->addWidget(m_selectionGroupBox);
  vLayout->addWidget(m_column_title);
  vLayout->addWidget(m_x_step_title);
  vLayout->addWidget(m_x_step);
  vLayout->addWidget(m_x_interval_title);
  vLayout->addWidget(x_interval_GroupBox);
  vLayout->addWidget(m_axisMinSliderX);
  vLayout->addWidget(m_axisMaxSliderX);
  vLayout->addWidget(m_row_title);
  vLayout->addWidget(m_z_step_title);
  vLayout->addWidget(m_z_step);
  vLayout->addWidget(m_z_interval_title);
  vLayout->addWidget(z_interval_GroupBox);
  vLayout->addWidget(m_axisMinSliderZ);
  vLayout->addWidget(m_axisMaxSliderZ);
  vLayout->addWidget(m_theme_title);

  vLayout->addWidget(m_colorGroupBox);
  setCentralWidget(m_widget);

  QObject::connect(
      m_surface, &Q3DSurface::selectedElementChanged, this,
      [this](QAbstract3DGraph::ElementType element) {
        if (element == QAbstract3DGraph::ElementNone) {
          statusBar()->showMessage(QString(""));
        } else {
          QVector3D val = m_modifier->getCurrentPoint();
          statusBar()->showMessage(
              QString("%1, %2, %3").arg(val.x()).arg(val.y()).arg(val.z()));
        }
      });
  QObject::connect(m_show_grid, &QCheckBox::toggled, m_modifier, &Plot::set_grid);
  QObject::connect(m_show_label, &QCheckBox::toggled, this, [this](bool enabled) {
    m_modifier->set_label(enabled);
  });
  QObject::connect(m_show_label_bordered, &QCheckBox::toggled, m_modifier,
                   &Plot::set_label_bordered);

  QObject::connect(m_sinc_dist_model_rb, &QRadioButton::toggled, this,
                   [this](bool enabled) {
                     if (enabled) {
                       m_modifier->switch_graph(PlotFunction::First);
                     }
                   });
  QObject::connect(m_sinc_x_sinc_z_model_rb, &QRadioButton::toggled, this,
                   [this](bool enabled) {
                     if (enabled) {
                       m_modifier->switch_graph(PlotFunction::Second);
                     }
                   });
  QObject::connect(m_modeNoneRB, &QRadioButton::toggled, this, [this]() {
    statusBar()->setVisible(false);
    m_modifier->toggleModeNone();
  });
  QObject::connect(m_modeItemRB, &QRadioButton::toggled, this, [this]() {
    statusBar()->setVisible(true);
    m_modifier->toggleModeItem();
  });
  QObject::connect(m_axisMinSliderX, &QSlider::valueChanged, this,
                   [this](int value) {
                     if (value >= m_axisMaxSliderX->value()) {
                       m_axisMaxSliderX->setValue(value + 1);
                     }
                     m_modifier->setAxisXRange(value, m_axisMaxSliderX->value());
                   });
  QObject::connect(m_axisMaxSliderX, &QSlider::valueChanged, this,
                   [this](int value) {
                     if (value <= m_axisMinSliderX->value()) {
                       m_axisMinSliderX->setValue(value - 1);
                     }
                     m_modifier->setAxisXRange(m_axisMinSliderX->value(), value);
                   });
  QObject::connect(m_axisMinSliderZ, &QSlider::valueChanged, this,
                   [this](int value) {
                     if (value >= m_axisMaxSliderZ->value()) {
                       m_axisMaxSliderZ->setValue(value + 1);
                     }
                     m_modifier->setAxisZRange(value, m_axisMaxSliderZ->value());
                   });
  QObject::connect(m_axisMaxSliderZ, &QSlider::valueChanged, this,
                   [this](int value) {
                     if (value <= m_axisMinSliderZ->value()) {
                       m_axisMinSliderZ->setValue(value - 1);
                     }
                     m_modifier->setAxisZRange(m_axisMinSliderZ->value(), value);
                   });
  QObject::connect(m_gradient_inferno, &QPushButton::pressed, m_modifier,
                   &Plot::set_inferno);
  QObject::connect(m_gradient_plasma, &QPushButton::pressed, m_modifier,
                   &Plot::set_plasma);

  statusBar()->hide();
  m_sinc_dist_model_rb->setChecked(true);
  m_modeItemRB->setChecked(true);

  m_actSave =
      new QAction(style()->standardIcon(QStyle::SP_DialogSaveButton), "", this);
  m_actSave->setShortcuts(QKeySequence::Save);

  connect(m_actSave, &QAction::triggered, this, &MainWindow::do_save);

  m_actRevert = new QAction(style()->standardIcon(QStyle::SP_DialogResetButton),
                          "", this);
  m_actRevert->setShortcuts(QKeySequence::Refresh);

  connect(m_actRevert, &QAction::triggered, this, &MainWindow::do_revert);

  m_actLangRu = new QAction(style()->standardIcon(QStyle::SP_DialogSaveButton),
                          "Русский", this);
  m_actLangRu->setStatusTip("Установить русский язык");
  connect(m_actLangRu, &QAction::triggered, this, &MainWindow::do_lang_ru);

  m_actLangEn = new QAction(style()->standardIcon(QStyle::SP_DialogResetButton),
                          "English", this);
  m_actLangEn->setStatusTip("Set english language");
  connect(m_actLangEn, &QAction::triggered, this, &MainWindow::do_lang_en);

  m_menu = menuBar()->addMenu("");
  m_menu->addAction(m_actSave);
  m_menu->addAction(m_actRevert);
  m_menu2 = menuBar()->addMenu("");
  m_menu2->addAction(m_actLangRu);
  m_menu2->addAction(m_actLangEn);
  m_toolbar = addToolBar("");
  m_toolbar->addAction(m_actSave);
  m_toolbar->addAction(m_actRevert);
  do_revert();
  updateText();
}

void MainWindow::updateText() {
  m_menu->setTitle(tr("Settings"));
  m_menu2->setTitle(tr("Language"));
  m_toolbar->setWindowTitle(tr("Toolbar"));
  m_actRevert->setText(tr("Revert"));
  m_actSave->setText(tr("Save"));
  m_sinc_dist_model_rb->setText(tr("sinc(distance_from_zero)"));
  m_sinc_x_sinc_z_model_rb->setText(tr("sinc(x) * sinc(z)"));
  m_colorGroupBox->setTitle(tr("Custom gradient"));
  m_selectionGroupBox->setTitle(tr("Selection Mode"));
  m_modeNoneRB->setText(tr("No selection"));
  m_modeItemRB->setText(tr("Item"));
  m_modelGroupBox->setTitle(tr("Plot"));
  m_actSave->setStatusTip(tr("Save state"));
  m_actRevert->setStatusTip(tr("Revert state"));
  m_widget->setWindowTitle(tr("Surface example"));
  m_show_grid->setText(tr("Show grid"));
  m_show_label->setText(tr("Show label"));
  m_show_label_bordered->setText(tr("Show label bordered"));
  m_column_title->setText(tr("Column range"));
  m_row_title->setText(tr("Row range"));
  m_theme_title->setText(tr("Theme"));
  m_x_step_title->setText(tr("Column step"));
  m_x_interval_title->setText(tr("Column interval"));
  m_z_step_title->setText(tr("Row step"));
  m_z_interval_title->setText(tr("Row interval"));
}

void MainWindow::do_save() {
  m_settings->setValue("axisMinSliderX", m_axisMinSliderX->value());
  m_settings->setValue("axisMinSliderZ", m_axisMinSliderZ->value());
  m_settings->setValue("axisMaxSliderX", m_axisMaxSliderX->value());
  m_settings->setValue("axisMaxSliderZ", m_axisMaxSliderZ->value());
  m_settings->setValue("show_grid", m_show_grid->isChecked());
  m_settings->setValue("show_label", m_show_label->isChecked());
  m_settings->setValue("show_label_bordered", m_show_label_bordered->isChecked());
  m_settings->setValue("sinc_dist_model_rb", m_sinc_dist_model_rb->isChecked());
  m_settings->setValue("modeNoneRB", m_modeNoneRB->isChecked());
  m_settings->setValue(
      "firstColor", static_cast<int>(m_modifier->m_colors.at(PlotFunction::First)));
  m_settings->setValue("secondColor", static_cast<int>(m_modifier->m_colors.at(
                                        PlotFunction::Second)));
  m_settings->setValue("translation", static_cast<int>(m_current_translation));
  m_settings->setValue("x_step", m_x_step->text());
  m_settings->setValue("x_begin", m_x_begin->text());
  m_settings->setValue("x_end", m_x_end->text());
  m_settings->setValue("z_step", m_z_step->text());
  m_settings->setValue("z_begin", m_z_begin->text());
  m_settings->setValue("z_end", m_z_end->text());
}
void MainWindow::do_revert() {
  m_show_grid->setChecked(m_settings->value("show_grid", false).toBool());
  m_show_label->setChecked(m_settings->value("show_label", false).toBool());
  m_show_label_bordered->setChecked(
      m_settings->value("show_label_bordered", false).toBool());

  m_axisMinSliderX->setValue(
      m_settings->value("axisMinSliderX", m_axisMinSliderX->minimum()).toInt());
  m_axisMinSliderZ->setValue(
      m_settings->value("axisMinSliderZ", m_axisMinSliderZ->minimum()).toInt());
  m_axisMaxSliderX->setValue(
      m_settings->value("axisMaxSliderX", m_axisMaxSliderX->maximum()).toInt());
  m_axisMaxSliderZ->setValue(
      m_settings->value("axisMaxSliderZ", m_axisMaxSliderZ->maximum()).toInt());

  ColorGradient first_state = static_cast<ColorGradient>(
      m_settings->value("firstColor", static_cast<int>(ColorGradient::Default))
          .toInt());
  ColorGradient second_state = static_cast<ColorGradient>(
      m_settings->value("secondColor", static_cast<int>(ColorGradient::Default))
          .toInt());
  m_modifier->updateColors({first_state, second_state});

  (m_settings->value("sinc_dist_model_rb", false).toBool()
       ? m_sinc_dist_model_rb
       : m_sinc_x_sinc_z_model_rb)
      ->setChecked(true);
  (m_settings->value("modeNoneRB", false).toBool() ? m_modeNoneRB : m_modeItemRB)
      ->setChecked(true);

  m_x_step->setText(m_settings->value("x_step", QString("50")).toString());
  m_x_begin->setText(m_settings->value("x_begin", QString("-10")).toString());
  m_x_end->setText(m_settings->value("x_end", QString("10")).toString());

  m_z_step->setText(m_settings->value("z_step", QString("50")).toString());
  m_z_begin->setText(m_settings->value("z_begin", QString("-10")).toString());
  m_z_end->setText(m_settings->value("z_end", QString("10")).toString());

  Translation translation = static_cast<Translation>(
      m_settings->value("translation", static_cast<int>(Translation::Default))
          .toInt());
  if (translation == Translation::English) {
    do_lang_en();
  } else if (translation == Translation::Russian) {
    do_lang_ru();
  }
}

void MainWindow::do_lang_ru() {
  if (m_qtLanguageTranslator.load(QString("Translation_ru"), ":/translations")) {
    qApp->installTranslator(&m_qtLanguageTranslator);
    m_current_translation = Translation::Russian;
    updateText();
  }
}
void MainWindow::do_lang_en() {
  if (m_qtLanguageTranslator.load(QString("Translation_en"), ":/translations")) {
    qApp->installTranslator(&m_qtLanguageTranslator);
    m_current_translation = Translation::English;
    updateText();
  }
}
