#ifndef PLOT_H
#define PLOT_H

#include "calculatable.h"
#include <QSettings>
#include <QtDataVisualization/Q3DSurface>
#include <QtDataVisualization/QHeightMapSurfaceDataProxy>
#include <QtDataVisualization/QSurface3DSeries>
#include <QtDataVisualization/QSurfaceDataProxy>
#include <QtWidgets/QSlider>

enum class ColorGradient { Default, Plasma, Inferno };

class Plot : public QObject {
  Q_OBJECT
public:
  explicit Plot(Q3DSurface *surface, QVector<Calculatable *> functions);
  ~Plot();

  void switch_graph(int index);

  void toggleModeNone() {
    m_graph->setSelectionMode(QAbstract3DGraph::SelectionNone);
  }
  void toggleModeItem() {
    m_graph->setSelectionMode(QAbstract3DGraph::SelectionItem);
  }
  void set_inferno();
  void set_plasma();
  void set_grid(bool enabled);
  void set_label(bool enabled);
  void set_label_bordered(bool enabled);
  void setAxisXRange(int min, int max);
  void setAxisZRange(int min, int max);
  void updateColors(QVector<ColorGradient> vals);
  Q3DSurface *m_graph;
  QVector3D getCurrentPoint();

  QVector<Calculatable *> m_functions;
  QVector<QSurface3DSeries *> m_series;
  QVector<ColorGradient> m_colors;
  QLinearGradient m_inferno_colors, m_plasma_colors;

  int m_count_x = 50;
  int m_count_z = 50;
  double m_min_x = -10.0f;
  double m_max_x = 10.0f;
  double m_min_z = -10.0f;
  double m_max_z = 10.0f;

  void changeIntervalX(double left, double right);
  void changeStepCountX(int value);

  void changeIntervalZ(double left, double right);
  void changeStepCountZ(int value);

private:
  int m_current_index = 0;
  QSurface3DSeries *m_sinc_series;
  QSurface3DSeries *m_sinc_x_sinc_z_series;
  void fillData(int index);
  void updateData();
};

#endif // PLOT_H
