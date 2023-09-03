#ifndef SECOND_FUNCTION_H
#define SECOND_FUNCTION_H

#include "calculatable.h"
#include <QtCore/qmath.h>

class SecondFunction : public Calculatable {
public:
  float calculate(float x, float z);
};

#endif // SECOND_FUNCTION_H
