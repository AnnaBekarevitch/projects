#include "plot.h"

#include <QtCore/qmath.h>
#include <QtDataVisualization/Q3DTheme>
#include <QtDataVisualization/QValue3DAxis>
// using namespace QtDataVisualization;

Plot::Plot(Q3DSurface *surface, QVector<Calculatable *> m_functions)
    : QObject(), m_graph(surface), m_colors(2, ColorGradient::Default),
      m_inferno_colors(0, 0, 1, 100), m_plasma_colors(0, 0, 1, 100),
      m_functions(m_functions) {
  m_graph->setAxisX(new QValue3DAxis);
  m_graph->setAxisY(new QValue3DAxis);
  m_graph->setAxisZ(new QValue3DAxis);
  m_graph->axisX()->setTitle(QString("x"));
  m_graph->axisY()->setTitle(QString("y"));
  m_graph->axisZ()->setTitle(QString("z"));
  m_sinc_series = new QSurface3DSeries();
  m_sinc_x_sinc_z_series = new QSurface3DSeries();
  m_sinc_series->setDrawMode(QSurface3DSeries::DrawSurfaceAndWireframe);
  m_sinc_series->setFlatShadingEnabled(true);
  m_graph->axisX()->setLabelAutoRotation(30);
  m_graph->axisY()->setLabelAutoRotation(90);
  m_graph->axisZ()->setLabelAutoRotation(30);
  m_series.append(m_sinc_series);
  m_series.append(m_sinc_x_sinc_z_series);

  updateData();

  m_inferno_colors.setColorAt(0.0, "#fcffa4");
  m_inferno_colors.setColorAt(0.50, "#f98e09");
  m_inferno_colors.setColorAt(0.60, "#bc3754");
  m_inferno_colors.setColorAt(0.70, "#57106e");
  m_inferno_colors.setColorAt(1, "#000004");

  m_plasma_colors.setColorAt(0.0, "#f0f921");
  m_plasma_colors.setColorAt(0.2, "#f89540");
  m_plasma_colors.setColorAt(0.4, "#cc4778");
  m_plasma_colors.setColorAt(0.6, "#7e03a8");
  m_plasma_colors.setColorAt(0.9, "#0d0887");

  set_grid(false);
  set_label(false);
  set_label_bordered(false);
}

Plot::~Plot() { delete m_graph; }

void Plot::fillData(int index) {
  float step_x = (m_max_x - m_min_x) / (m_count_x - 1);
  float step_z = (m_max_z - m_min_z) / (m_count_z - 1);

  QSurfaceDataArray *data = new QSurfaceDataArray;
  data->reserve(m_count_z);
  for (int i = 0; i < m_count_z; i++) {
    QSurfaceDataRow *new_row = new QSurfaceDataRow(m_count_x);
    float z = qMin(m_max_z, (i * step_z + m_min_z));
    for (int j = 0; j < m_count_x; j++) {
      float x = qMin(m_max_x, (j * step_x + m_min_x));
      (*new_row)[j].setPosition(
          QVector3D(x, m_functions.at(index)->calculate(x, z), z));
    }
    *data << new_row;
  }

  m_series.at(index)->dataProxy()->resetArray(data);
}

void Plot::switch_graph(int index) {
  m_graph->removeSeries(m_series.at(m_current_index));
  m_graph->addSeries(m_series.at(index));
  m_current_index = index;
}

void Plot::setAxisXRange(int min, int max) {
  float left = static_cast<double>(m_max_x - m_min_x) / (m_count_x - 1) * min + m_min_x;
  float right =
      static_cast<double>(m_max_x - m_min_x) / (m_count_x - 1) * max + m_min_x;
  m_graph->axisX()->setRange(left, right);
}

void Plot::setAxisZRange(int min, int max) {
  float left = static_cast<double>(m_max_x - m_min_x) / (m_count_z - 1) * min + m_min_z;
  float right =
      static_cast<double>(m_max_z - m_min_z) / (m_count_z - 1) * max + m_min_z;
  m_graph->axisZ()->setRange(left, right);
}

void Plot::set_inferno() {
  m_series.at(m_current_index)->setBaseGradient(m_inferno_colors);
  m_series.at(m_current_index)->setColorStyle(Q3DTheme::ColorStyleRangeGradient);
  m_colors.replace(m_current_index, ColorGradient::Inferno);
}

void Plot::set_plasma() {

  m_series.at(m_current_index)->setBaseGradient(m_plasma_colors);
  m_series.at(m_current_index)->setColorStyle(Q3DTheme::ColorStyleRangeGradient);
  m_colors.replace(m_current_index, ColorGradient::Plasma);
}

void Plot::set_grid(bool enabled) {
  m_graph->activeTheme()->setGridEnabled(bool(enabled));
}
void Plot::set_label(bool enabled) {
  m_graph->axisX()->setLabelFormat(enabled ? "%.2f" : "");
  m_graph->axisZ()->setLabelFormat(enabled ? "%.2f" : "");
  m_graph->axisY()->setLabelFormat(enabled ? "%.2f" : "");
  m_graph->axisX()->setTitleVisible(enabled);
  m_graph->axisY()->setTitleVisible(enabled);
  m_graph->axisZ()->setTitleVisible(enabled);
  for (QSurface3DSeries *v : m_series) {
    if (enabled) {
      v->setItemLabelFormat(QStringLiteral("@xLabel,@yLabel,@zLabel"));
    } else {
      v->setItemLabelFormat(QString(""));
    }
  }
}
void Plot::set_label_bordered(bool enabled) {
  m_graph->activeTheme()->setLabelBackgroundEnabled(enabled);
}

void Plot::updateColors(QVector<ColorGradient> vals) {
  for (int ind = 0; ind < vals.size(); ind++) {
    if (vals.at(ind) == ColorGradient::Default)
      continue;
    if (vals.at(ind) == ColorGradient::Inferno) {
      m_series.at(ind)->setBaseGradient(m_inferno_colors);
    } else if (vals.at(ind) == ColorGradient::Plasma) {
      m_series.at(ind)->setBaseGradient(m_plasma_colors);
    }
    m_series.at(ind)->setColorStyle(Q3DTheme::ColorStyleRangeGradient);
  }
  m_colors = vals;
}

void Plot::updateData() {
  for (int i = 0; i < m_series.size(); i++) {
    fillData(i);
  }
}

QVector3D Plot::getCurrentPoint() {
  QPoint val = m_series.at(m_current_index)->selectedPoint();
  return m_series.at(m_current_index)->dataProxy()->itemAt(val)->position();
}

void Plot::changeIntervalX(double left, double right) {
  m_min_x = left;
  m_max_x = right;
  updateData();
}

void Plot::changeStepCountX(int val) {
  m_count_x = val;
  updateData();
}

void Plot::changeIntervalZ(double left, double right) {
  m_min_z = left;
  m_max_z = right;
  updateData();
}

void Plot::changeStepCountZ(int val) {
  m_count_z = val;
  updateData();
}
