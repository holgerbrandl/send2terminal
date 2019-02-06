## Interactive Test Suite for send2terminal


## eval current line (no extra linebreak
1 + 1


## eval expression under caret

iris[- 5] %>% head() %>% str


## eval compex expression with pipe and ggplot

mtcars %>%
    correlate() %>%
    focus(mpg) %>%
    ggplot(aes(x = reorder(rowname, mpg), y = mpg)) +
    geom_bar(stat = "identity") +
    ylab("Correlation with mpg") +
    xlab("Variable")




## evaulate up to function declartion

myFun <- function(input, output) {
    # For storing which rows have been excluded
    vals <- reactiveValues(   keeprows = rep(TRUE, nrow(fishLines)))

    internal_fun = function(x){
        x * 2 # caret after 2

    }

    internal_fun(1)
}
