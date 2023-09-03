#include "mainwindow.h"
#include <QApplication>
#include <QTranslator>

int main(int argc, char **argv) {
  QApplication app(argc, argv);

  QCoreApplication::setOrganizationName("surfaces");

  MainWindow window;
  window.show();

  return app.exec();
}
