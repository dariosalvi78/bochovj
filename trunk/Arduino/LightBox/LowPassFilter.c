#include "LowPassFilter.h"

static int filter_taps[LOWPASSFILTER_TAP_NUM] = {
  442,
  300,
  248,
  40,
  -348,
  -893,
  -1524,
  -2119,
  -2520,
  -2563,
  -2114,
  -1100,
  462,
  2452,
  4659,
  6811,
  8617,
  9819,
  10241,
  9819,
  8617,
  6811,
  4659,
  2452,
  462,
  -1100,
  -2114,
  -2563,
  -2520,
  -2119,
  -1524,
  -893,
  -348,
  40,
  248,
  300,
  442
};

void LowPassFilter_init(LowPassFilter* f) {
  int i;
  for(i = 0; i < LOWPASSFILTER_TAP_NUM; ++i)
    f->history[i] = 0;
  f->last_index = 0;
}

void LowPassFilter_put(LowPassFilter* f, int input) {
  f->history[f->last_index++] = input;
  if(f->last_index == LOWPASSFILTER_TAP_NUM)
    f->last_index = 0;
}

int LowPassFilter_get(LowPassFilter* f) {
  long acc = 0;
  int index = f->last_index, i;
  for(i = 0; i < LOWPASSFILTER_TAP_NUM; ++i) {
    index = index != 0 ? index-1 : LOWPASSFILTER_TAP_NUM-1;
    acc += (long)f->history[index] * filter_taps[i];
  };
  return acc >> 16;
}

