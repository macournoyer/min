#include "min.h"

int main (int argc, char const *argv[]) {
  min_parse("ohaie(1, \"me\")\n  deep\n  # hi\n    down\n  1\n\n");
  return 0;
}