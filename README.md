# Running Application
A gradle wrapper is provided. To run the application following can be executed from the java-test directory. 

`./gradlew run`

After running the above following options will be printed on screen and are available:

```$xslt
Please enter one of the following
<product name> <quantity> - e.g, apples 5
checkout <day> - The day on which to checkout 0 is today 1 is tomorrow and so on
reset - To clear basket
exit - To close the application and exit
```

To add a product simply enter product name followed by the quantity and press enter.

`apples 3` (Then hit Enter)

A message will be printed 

`apples with quantity 3 added successfully`

More products can be added 

`soup 2` (Enter)

`soup with quantity 2 added successfully`

`bread 1` (Enter)

`bread with quantity 1 added successfully`

To checkout just enter checkout followed by the day you wish to checkout. Day 0 is today, 1 is tomorrow and so on.

`checkout 5` (Enter)

Basket cost will be printed.
 
```$xslt
Total cost of basket is 1.97
Basket has been reset successfully
```

# To reset the basket and start over

`reset` 

# To quit application

`exit` Followed by Enter

or 

Ctrl + C 
