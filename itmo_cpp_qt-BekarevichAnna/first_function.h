#ifndef FIRST_FUNCTION_H
#define FIRST_FUNCTION_H

#include "calculatable.h"
#include <QtCore/qmath.h>

class FirstFunction : public Calculatable {
public:
  float calculate(float x, float z);
};

#endif // FIRST_FUNCTION_H
