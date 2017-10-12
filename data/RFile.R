library(readr)
#======================================================================
#SIGNIFICANCE IN WOLVES KILLED RBDI vs MDP
resultsRBDI1 <- read_csv("~/Downloads/data/newRBDI_Result/Simulation_012/results.csv", 
                         col_types = cols(X12 = col_skip()))
resultsRBDI1$Iteration = factor(resultsRBDI1$Iteration) # convert to nominal factor

resultsMDP1 <- read_csv("~/Downloads/data/MDP_Result/Simulation_00/results.csv", 
                        col_types = cols(X12 = col_skip()))
resultsMDP1$Iteration = factor(resultsMDP1$Iteration) # convert to nominal factor

resultsRBDI2 <- read_csv("~/Downloads/data/newRBDI_Result/Simulation_014/results.csv", 
                         col_types = cols(X12 = col_skip()))
resultsRBDI2$Iteration = factor(resultsRBDI2$Iteration) # convert to nominal factor

resultsMDP2 <- read_csv("~/Downloads/data/MDP_Result/Simulation_03/results.csv", 
                        col_types = cols(X12 = col_skip()))
resultsMDP2$Iteration = factor(resultsMDP2$Iteration) # convert to nominal factor


resultsRBDI3 <- read_csv("~/Downloads/data/newRBDI_Result/Simulation_015/results.csv", 
                         col_types = cols(X12 = col_skip()))
resultsRBDI3$Iteration = factor(resultsRBDI3$Iteration) # convert to nominal factor

resultsMDP3 <- read_csv("~/Downloads/data/MDP_Result/Simulation_05/results.csv", 
                        col_types = cols(X12 = col_skip()))
resultsMDP3$Iteration = factor(resultsMDP3$Iteration) # convert to nominal factor


shapiro.test(resultsRBDI1$Predators_Killed)
shapiro.test(resultsRBDI2$Predators_Killed)
shapiro.test(resultsRBDI3$Predators_Killed)

shapiro.test(resultsRBDI1$Survival_Time)
shapiro.test(resultsRBDI2$Survival_Time)
shapiro.test(resultsRBDI3$Survival_Time)

shapiro.test(resultsRBDI1$Score)
shapiro.test(resultsRBDI2$Score)
shapiro.test(resultsRBDI3$Score)


shapiro.test(resultsMDP1$Predators_Killed)
shapiro.test(resultsMDP2$Predators_Killed)
shapiro.test(resultsMDP3$Predators_Killed)

shapiro.test(resultsMDP1$Survival_Time)
shapiro.test(resultsMDP2$Survival_Time)
shapiro.test(resultsMDP3$Survival_Time)

shapiro.test(resultsMDP1$Score)
shapiro.test(resultsMDP2$Score)
shapiro.test(resultsMDP3$Score)



#Data is non normally distrubuted if p<0.05 for shapiro wilk so reject null hypotheis
#if >0.05 is normally distributed


#=================================
killdf <- data.frame(resultsRBDI1$Predators_Killed, resultsRBDI2$Predators_Killed, resultsRBDI3$Predators_Killed)
sdf <- stack(killdf)
shapiro.test(sdf$values)
#==================================

#=================================
survdf <- data.frame(resultsRBDI1$Survival_Time, resultsRBDI2$Survival_Time, resultsRBDI3$Survival_Time)
sdf <- stack(survdf)
shapiro.test(sdf$values)
#==================================

#=================================
scrdf <- data.frame(resultsRBDI1$Score, resultsRBDI2$Score, resultsRBDI3$Score)
sdf <- stack(scrdf)
shapiro.test(sdf$values)
#==================================





wilcox.test(resultsRBDI1$Predators_Killed,resultsMDP1$Predators_Killed)
wilcox.test(resultsRBDI2$Predators_Killed,resultsMDP2$Predators_Killed)
wilcox.test(resultsRBDI3$Predators_Killed,resultsMDP3$Predators_Killed)

wilcox.test(resultsRBDI1$Score,resultsMDP1$Score)
wilcox.test(resultsRBDI2$Score,resultsMDP2$Score)
wilcox.test(resultsRBDI3$Score,resultsMDP3$Score)

#if p<0.05 then we have significance and reject null hypothesis
#Wilcox AKA Mann-Witney U test 

t.test(resultsRBDI1$Predators_Killed,resultsMDP1$Predators_Killed)
t.test(resultsRBDI2$Predators_Killed,resultsMDP2$Predators_Killed)
t.test(resultsRBDI3$Predators_Killed,resultsMDP3$Predators_Killed)

t.test(resultsRBDI1$Survival_Time,resultsMDP1$Survival_Time)
t.test(resultsRBDI2$Survival_Time,resultsMDP2$Survival_Time)
t.test(resultsRBDI3$Survival_Time,resultsMDP3$Survival_Time)

t.test(resultsRBDI1$Score,resultsMDP1$Score)
t.test(resultsRBDI2$Score,resultsMDP2$Score)
t.test(resultsRBDI3$Score,resultsMDP3$Score)

#if p<0.05 then we have significance and reject null hypothesis
#independant samples t-test 

#=========================================================================================
#SIGNIFICANCE IN  RBDI vs POMDP

resultsPOMDP1 <- read_csv("~/Downloads/data/POMDP_Result/Simulation_014/results.csv", 
                          col_types = cols(X12 = col_skip()))
resultsPOMDP1$Iteration = factor(resultsPOMDP1$Iteration) # convert to nominal factor


resultsPOMDP2 <- read_csv("~/Downloads/data/POMDP_Result/Simulation_016/results.csv", 
                          col_types = cols(X12 = col_skip()))
resultsPOMDP2$Iteration = factor(resultsPOMDP2$Iteration) # convert to nominal factor

resultsPOMDP3 <- read_csv("~/Downloads/data/POMDP_Result/Simulation_018/results.csv", 
                          col_types = cols(X12 = col_skip()))
resultsPOMDP3$Iteration = factor(resultsPOMDP3$Iteration) # convert to nominal factor



shapiro.test(resultsPOMDP1$Predators_Killed)
shapiro.test(resultsPOMDP2$Predators_Killed)
shapiro.test(resultsPOMDP3$Predators_Killed)

shapiro.test(resultsPOMDP1$Survival_Time)
shapiro.test(resultsPOMDP2$Survival_Time)
shapiro.test(resultsPOMDP3$Survival_Time)

shapiro.test(resultsPOMDP1$Score)
shapiro.test(resultsPOMDP2$Score)
shapiro.test(resultsPOMDP3$Score)

#Data is non normally distrubuted p<0.05 for shapiro wilk so reject null hypotheis


wilcox.test(resultsRBDI1$Predators_Killed,resultsPOMDP1$Predators_Killed)
wilcox.test(resultsRBDI2$Predators_Killed,resultsPOMDP2$Predators_Killed)
wilcox.test(resultsRBDI3$Predators_Killed,resultsPOMDP3$Predators_Killed)


wilcox.test(resultsRBDI1$Score,resultsPOMDP1$Score)
wilcox.test(resultsRBDI2$Score,resultsPOMDP2$Score)
wilcox.test(resultsRBDI3$Score,resultsPOMDP3$Score)



#if p<0.05 then we have significance and reject null hypothesis
#Wilcox AKA Mann-Witney U test 


t.test(resultsRBDI1$Predators_Killed,resultsPOMDP1$Predators_Killed)
t.test(resultsRBDI2$Predators_Killed,resultsPOMDP2$Predators_Killed)
t.test(resultsRBDI3$Predators_Killed,resultsPOMDP3$Predators_Killed)

t.test(resultsRBDI1$Score,resultsPOMDP1$Score)
t.test(resultsRBDI2$Score,resultsPOMDP2$Score)
t.test(resultsRBDI3$Score,resultsPOMDP3$Score)

t.test(resultsRBDI1$Survival_Time,resultsPOMDP1$Survival_Time)
t.test(resultsRBDI2$Survival_Time,resultsPOMDP2$Survival_Time)
t.test(resultsRBDI3$Survival_Time,resultsPOMDP3$Survival_Time)

#if p<0.05 then we have significance and reject null hypothesis
#independant samples t-test 
#===========================================================================================
#=========================================================================================
#SIGNIFICANCE IN WOLVES KILLED RBDI vs BDI

resultsBDI1 <- read_csv("~/Downloads/data/PLAIN_BDI_RESULT/Simulation_00/results.csv", 
                          col_types = cols(X12 = col_skip()))
resultsBDI1$Iteration = factor(resultsBDI1$Iteration) # convert to nominal factor


resultsBDI2 <- read_csv("~/Downloads/data/PLAIN_BDI_RESULT/Simulation_02/results.csv", 
                          col_types = cols(X12 = col_skip()))
resultsBDI2$Iteration = factor(resultsBDI2$Iteration) # convert to nominal factor

resultsBDI3 <- read_csv("~/Downloads/data/PLAIN_BDI_RESULT/Simulation_04/results.csv", 
                          col_types = cols(X12 = col_skip()))
resultsBDI3$Iteration = factor(resultsBDI3$Iteration) # convert to nominal factor



shapiro.test(resultsBDI1$Predators_Killed)
shapiro.test(resultsBDI2$Predators_Killed)
shapiro.test(resultsBDI3$Predators_Killed)

shapiro.test(resultsBDI1$Survival_Time)
shapiro.test(resultsBDI2$Survival_Time)
shapiro.test(resultsBDI3$Survival_Time)

shapiro.test(resultsBDI1$Score)
shapiro.test(resultsBDI2$Score)
shapiro.test(resultsBDI3$Score)


#Data is non normally distrubuted p<0.05 for shapiro wilk so reject null hypotheis


wilcox.test(resultsRBDI1$Predators_Killed,resultsBDI1$Predators_Killed)
wilcox.test(resultsRBDI2$Predators_Killed,resultsBDI2$Predators_Killed)
wilcox.test(resultsRBDI3$Predators_Killed,resultsBDI3$Predators_Killed)


wilcox.test(resultsRBDI1$Survival_Time,resultsBDI1$Survival_Time)
wilcox.test(resultsRBDI2$Survival_Time,resultsBDI2$Survival_Time)
wilcox.test(resultsRBDI3$Survival_Time,resultsBDI3$Survival_Time)

wilcox.test(resultsRBDI1$Score,resultsBDI1$Score)
wilcox.test(resultsRBDI2$Score,resultsBDI2$Score)
wilcox.test(resultsRBDI3$Score,resultsBDI3$Score)


#if p<0.05 then we have significance and reject null hypothesis
#Wilcox AKA Mann-Witney U test 


t.test(resultsRBDI1$Predators_Killed,resultsBDI1$Predators_Killed)
t.test(resultsRBDI2$Predators_Killed,resultsBDI2$Predators_Killed)
t.test(resultsRBDI3$Predators_Killed,resultsBDI3$Predators_Killed)

t.test(resultsRBDI1$Survival_Time,resultsBDI1$Survival_Time)
t.test(resultsRBDI2$Survival_Time,resultsBDI2$Survival_Time)
t.test(resultsRBDI3$Survival_Time,resultsBDI3$Survival_Time)

t.test(resultsRBDI1$Score,resultsBDI1$Score)
t.test(resultsRBDI2$Score,resultsBDI2$Score)
t.test(resultsRBDI3$Score,resultsBDI3$Score)

#if p<0.05 then we have significance and reject null hypothesis
#independant samples t-test 
#===========================================================================================



mean(mean(resultsRBDI1$Predators_Killed),mean(resultsRBDI2$Predators_Killed),mean(resultsRBDI3$Predators_Killed))
mean(mean(resultsRBDI1$Score),mean(resultsRBDI2$Score),mean(resultsRBDI3$Score))
mean(mean(resultsRBDI1$Survival_Time),mean(resultsRBDI2$Survival_Time),mean(resultsRBDI3$Survival_Time))

dataRBDI <- data.frame(A = c(resultsRBDI1$Predators_Killed), B = c(resultsRBDI1$Predators_Total))
dataRBDI$C <- ((dataRBDI$A / dataRBDI$B)*100)
mean(dataRBDI$C)

dataRBDI2 <- data.frame(A = c(resultsRBDI2$Predators_Killed), B = c(resultsRBDI2$Predators_Total))
dataRBDI2$C <- ((dataRBDI2$A / dataRBDI2$B)*100)
mean(dataRBDI2$C)

dataRBDI3 <- data.frame(A = c(resultsRBDI3$Predators_Killed), B = c(resultsRBDI3$Predators_Total))
dataRBDI3$C <- ((dataRBDI3$A / dataRBDI3$B)*100)
mean(dataRBDI3$C)

mean(mean(dataRBDI$C),mean(dataRBDI2$C),mean(dataRBDI3$C))


timeRBDI <- data.frame(A = c(resultsRBDI1$Survival_Time), B = c(resultsRBDI1$Previous_Survival_Time))
timeRBDI$C <- ((timeRBDI$A - timeRBDI$B))
mean(timeRBDI$C)

timeRBDI2 <- data.frame(A = c(resultsRBDI2$Survival_Time), B = c(resultsRBDI2$Previous_Survival_Time))
timeRBDI2$C <- ((timeRBDI2$A - timeRBDI2$B))
mean(timeRBDI2$C)

timeRBDI3 <- data.frame(A = c(resultsRBDI3$Survival_Time), B = c(resultsRBDI3$Previous_Survival_Time))
timeRBDI3$C <- ((timeRBDI3$A - timeRBDI3$B))
mean(timeRBDI3$C)

mean(mean(timeRBDI$C),mean(timeRBDI2$C),mean(timeRBDI3$C))

timeBDI <- data.frame(A = c(resultsBDI1$Survival_Time), B = c(resultsBDI1$Previous_Survival_Time))
timeBDI$C <- ((timeBDI$A - timeBDI$B))
mean(timeBDI$C)

timeBDI2 <- data.frame(A = c(resultsBDI2$Survival_Time), B = c(resultsBDI2$Previous_Survival_Time))
timeBDI2$C <- ((timeBDI2$A - timeBDI2$B))
mean(timeBDI2$C)

timeBDI3 <- data.frame(A = c(resultsBDI3$Survival_Time), B = c(resultsBDI3$Previous_Survival_Time))
timeBDI3$C <- ((timeBDI3$A - timeBDI3$B))
mean(timeBDI3$C)

mean(mean(timeBDI$C),mean(timeBDI2$C),mean(timeBDI3$C))



timeMDP <- data.frame(A = c(resultsMDP1$Survival_Time), B = c(resultsMDP1$Previous_Survival_Time))
timeMDP$C <- ((timeMDP$A - timeMDP$B))
mean(timeMDP$C)

timeMDP2 <- data.frame(A = c(resultsMDP2$Survival_Time), B = c(resultsMDP2$Previous_Survival_Time))
timeMDP2$C <- ((timeMDP2$A - timeMDP2$B))
mean(timeMDP2$C)

timeMDP3 <- data.frame(A = c(resultsMDP3$Survival_Time), B = c(resultsMDP3$Previous_Survival_Time))
timeMDP3$C <- ((timeMDP3$A - timeMDP3$B))
mean(timeMDP3$C)

mean(mean(timeMDP$C),mean(timeMDP2$C),mean(timeMDP3$C))


timePOMDP <- data.frame(A = c(resultsPOMDP1$Survival_Time), B = c(resultsPOMDP1$Previous_Survival_Time))
timePOMDP$C <- ((timePOMDP$A - timePOMDP$B))
mean(timePOMDP$C)

timePOMDP2 <- data.frame(A = c(resultsPOMDP2$Survival_Time), B = c(resultsPOMDP2$Previous_Survival_Time))
timePOMDP2$C <- ((timePOMDP2$A - timePOMDP2$B))
mean(timePOMDP2$C)

timePOMDP3 <- data.frame(A = c(resultsPOMDP3$Survival_Time), B = c(resultsPOMDP3$Previous_Survival_Time))
timePOMDP3$C <- ((timePOMDP3$A - timePOMDP3$B))
mean(timePOMDP3$C)

mean(mean(timePOMDP$C),mean(timePOMDP2$C),mean(timePOMDP3$C))





shapiro.test(timeRBDI$C)
shapiro.test(timeRBDI2$C)
shapiro.test(timeRBDI3$C)

t.test(timeRBDI$C,timeBDI$C)
t.test(timeRBDI2$C,timeBDI2$C)
t.test(timeRBDI3$C,timeBDI3$C)

t.test(timeRBDI$C,timeMDP$C)
t.test(timeRBDI2$C,timeMDP2$C)
t.test(timeRBDI3$C,timeMDP3$C)

t.test(timeRBDI$C,timePOMDP$C)
t.test(timeRBDI2$C,timePOMDP2$C)
t.test(timeRBDI3$C,timePOMDP3$C)


write.csv(timeRBDI, file = "ptRBDI1.csv")
write.csv(timeRBDI2, file = "ptRBDI2.csv")
write.csv(timeRBDI2, file = "ptRBDI3.csv")


dataBDI <- data.frame(A = c(resultsBDI1$Predators_Killed), B = c(resultsBDI1$Predators_Total))
dataBDI$C <- ((dataBDI$A / dataBDI$B)*100)
mean(dataBDI$C)

dataBDI2 <- data.frame(A = c(resultsBDI2$Predators_Killed), B = c(resultsBDI2$Predators_Total))
dataBDI2$C <- ((dataBDI2$A / dataBDI2$B)*100)
mean(dataBDI2$C)

dataBDI3 <- data.frame(A = c(resultsBDI3$Predators_Killed), B = c(resultsBDI3$Predators_Total))
dataBDI3$C <- ((dataBDI3$A / dataBDI3$B)*100)
mean(dataBDI3$C)

mean(mean(dataBDI$C),mean(dataBDI2$C),mean(dataBDI3$C))

dataMDP <- data.frame(A = c(resultsMDP1$Predators_Killed), B = c(resultsMDP1$Predators_Total))
dataMDP$C <- ((dataMDP$A / dataMDP$B)*100)
mean(dataMDP$C)

dataMDP2 <- data.frame(A = c(resultsMDP2$Predators_Killed), B = c(resultsMDP2$Predators_Total))
dataMDP2$C <- ((dataMDP2$A / dataMDP2$B)*100)
mean(dataMDP2$C)

dataMDP3 <- data.frame(A = c(resultsMDP3$Predators_Killed), B = c(resultsMDP3$Predators_Total))
dataMDP3$C <- ((dataMDP3$A / dataMDP3$B)*100)
mean(dataMDP3$C)

mean(mean(dataMDP$C),mean(dataMDP2$C),mean(dataMDP3$C))


dataPOMDP <- data.frame(A = c(resultsPOMDP1$Predators_Killed), B = c(resultsPOMDP1$Predators_Total))
dataPOMDP$C <- ((dataPOMDP$A / dataPOMDP$B)*100)
mean(dataPOMDP$C)

dataPOMDP2 <- data.frame(A = c(resultsPOMDP2$Predators_Killed), B = c(resultsPOMDP2$Predators_Total))
dataPOMDP2$C <- ((dataPOMDP2$A / dataPOMDP2$B)*100)
mean(dataPOMDP2$C)

dataPOMDP3 <- data.frame(A = c(resultsPOMDP3$Predators_Killed), B = c(resultsPOMDP3$Predators_Total))
dataPOMDP3$C <- ((dataPOMDP3$A / dataPOMDP3$B)*100)
mean(dataPOMDP3$C)

mean(mean(dataPOMDP$C),mean(dataPOMDP2$C),mean(dataPOMDP3$C))



mean(mean(resultsBDI1$Predators_Killed),mean(resultsBDI2$Predators_Killed),mean(resultsBDI3$Predators_Killed))
mean(mean(resultsBDI1$Score),mean(resultsBDI2$Score),mean(resultsBDI3$Score))
mean(mean(resultsBDI1$Survival_Time),mean(resultsBDI2$Survival_Time),mean(resultsBDI3$Survival_Time))


mean(mean(resultsMDP1$Predators_Killed),mean(resultsMDP2$Predators_Killed),mean(resultsMDP3$Predators_Killed))
mean(mean(resultsMDP1$Score),mean(resultsMDP2$Score),mean(resultsMDP3$Score))
mean(mean(resultsMDP1$Survival_Time),mean(resultsMDP2$Survival_Time),mean(resultsMDP3$Survival_Time))


mean(mean(resultsPOMDP1$Predators_Killed),mean(resultsPOMDP2$Predators_Killed),mean(resultsPOMDP3$Predators_Killed))
mean(mean(resultsPOMDP1$Score),mean(resultsPOMDP2$Score),mean(resultsPOMDP3$Score))
mean(mean(resultsPOMDP1$Survival_Time),mean(resultsPOMDP2$Survival_Time),mean(resultsPOMDP3$Survival_Time))



