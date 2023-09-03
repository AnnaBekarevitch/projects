#include "first_function.h"

float FirstFunction::calculate(float x, float z) {
  float dist = qSqrt(x * x + z * z);
  float y = dist == 0 ? 1 : qSin(dist) / dist;
  return y;
}
