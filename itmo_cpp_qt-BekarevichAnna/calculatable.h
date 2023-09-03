#ifndef CALCULATABLE_H
#define CALCULATABLE_H

class Calculatable {
public:
  virtual float calculate(float x, float z) = 0;
  virtual ~Calculatable(){};
};

#endif // CALCULATABLE_H
