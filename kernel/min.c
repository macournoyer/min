#include <stdio.h>
#include <sys/stat.h>
#include "min.h"

static int usage() {
  printf("usage: min [options] [file]\n"
         "options:\n"
         "  -e   eval code\n"
         "  -V   show debug info\n"
         "  -v   print version\n"
         "  -h   print this\n");
  return 1;
}

static int version() {
  printf("min %s\n", MIN_VERSION);
  return 1;
}

static int eval(char *code, char *filename, int verbose) {
  struct MinLobby *lobby = MinLobby();
  
  OBJ msg = min_parse(lobby, code, filename);
  if (verbose) printf("message chain: %s\n", MIN_STR_PTR(min_send2(msg, "inspect")));
  MinMessage_eval_on(lobby, 0, msg, lobby->lobby, lobby->lobby);
  
  MinLobby_destroy(lobby);
  return 0;
}

static int eval_file(char *filename, int verbose) {
  FILE *fp;
  struct stat stats;
  
  if (stat(filename, &stats) == -1) {
    perror("File does not exist");
    return 1;
  }
  
  fp = fopen(filename, "rb");
  if (!fp) {
    perror("Error opening file");
    return 1;
  }
  
  char *buf = MIN_ALLOC_N(char, stats.st_size + 1);
  
  if (fread(buf, 1, stats.st_size, fp) == stats.st_size) {
    eval(buf, filename, verbose);
  } else {
    perror("Could not read entire file");
  }
  
  free(buf);
  
  return 0;
}

#define OPTION(n) if (strcmp(argv[i], n) == 0)

int main (int argc, char *argv[]) {
  int verbose = 0;
  int i;
  
  if (argc > 1) {
    for (i = 0; i < argc; i++) {
      OPTION("-e") {
        return eval(argv[++i], "<eval>", verbose);
      }
      OPTION("-v") {
        return version();
      }
      OPTION("-V") {
        verbose = 1;
        continue;
      }
      OPTION("-h") {
        return usage();
      }
    }
    return eval_file(argv[1], verbose);
  }
  
  return usage();
}