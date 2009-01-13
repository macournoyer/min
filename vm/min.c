#include "min.h"

/* void test_indent() {
  min_parse("ohaie(1, \"me\"):\n  @deep\n  # hi\n    down\n  1\n\n");
} */

int main (int argc, char const *argv[]) {
  struct MinVM *vm = min_create();

  /* min_parse("print(1)\nohaie"); */

  min_destroy(vm);
  return 0;
}