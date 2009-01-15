#ifndef _NUMBER_H_
#define _NUMBER_H_

#define MIN_NUM_FLAG  0x01
#define INT2NUM(i)    (OBJ)((i) << TR_SHIFT | MIN_NUM_FLAG)
#define NUM2INT(i)    (int)((i) >> TR_SHIFT)

struct MinNumber {
  MIN_OBJ_HEADER;
  int   i;
};

#endif /* _NUMBER_H_ */
