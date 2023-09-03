#include "second_function.h"

float SecondFunction ::calculate(float x, float z) {
  float sinc_z = z == 0 ? 1 : qSin(z) / z;
  float sinc_x = x == 0 ? 1 : qSin(x) / x;
  float y = sinc_x * sinc_z;
  return y;
}
