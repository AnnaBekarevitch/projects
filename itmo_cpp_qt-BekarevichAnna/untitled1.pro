QT += datavisualization

SOURCES += main.cpp \
           first_function.cpp \
           mainwindow.cpp \
           plot.cpp \
           second_function.cpp

HEADERS += \
    calculatable.h \
    first_function.h \
    mainwindow.h \
    plot.h \
    second_function.h

QT += widgets
requires(qtConfig(combobox))

OTHER_FILES += doc/src/* \
               doc/images/*

TRANSLATIONS += Translation_ru.ts
TRANSLATIONS += Translation_en.ts

CODECFORSRC     = UTF-8

RESOURCES += \
    translations.qrc
