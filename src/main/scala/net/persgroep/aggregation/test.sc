val list = List("apple", "mango", "orange")

list.foldLeft(" ")((a,b) => a + b + "Fruit")