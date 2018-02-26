{
    "nbformat_minor": 1, 
    "cells": [
        {
            "execution_count": 3, 
            "cell_type": "code", 
            "metadata": {}, 
            "outputs": [], 
            "source": "// The code was removed by DSX for sharing."
        }, 
        {
            "execution_count": 4, 
            "cell_type": "code", 
            "metadata": {}, 
            "outputs": [
                {
                    "output_type": "stream", 
                    "name": "stdout", 
                    "text": "Creating DataFrame, this will take a few moments...\nDataFrame created.\n"
                }
            ], 
            "source": "val df1 = ProjectUtil.loadDataFrameFromFile(pc,\"ex1data1Spark.txt\")"
        }, 
        {
            "execution_count": 5, 
            "cell_type": "code", 
            "metadata": {}, 
            "outputs": [
                {
                    "output_type": "stream", 
                    "name": "stdout", 
                    "text": "[6.1101,17.592]\n[5.5277,9.1302]\n[8.5186,13.662]\n[7.0032,11.854]\n[5.8598,6.8233]\n"
                }
            ], 
            "source": "df1.take(5).foreach(println)"
        }, 
        {
            "execution_count": 4, 
            "cell_type": "code", 
            "metadata": {}, 
            "outputs": [
                {
                    "output_type": "stream", 
                    "name": "stdout", 
                    "text": "+------+-------+\n| Store| Profit|\n+------+-------+\n|6.1101| 17.592|\n|5.5277| 9.1302|\n|8.5186| 13.662|\n|7.0032| 11.854|\n|5.8598| 6.8233|\n|8.3829| 11.886|\n|7.4764| 4.3483|\n|8.5781|   12.0|\n|6.4862| 6.5987|\n|5.0546| 3.8166|\n|5.7107| 3.2522|\n|14.164| 15.505|\n| 5.734| 3.1551|\n|8.4084| 7.2258|\n|5.6407|0.71618|\n|5.3794| 3.5129|\n|6.3654| 5.3048|\n|5.1301|0.56077|\n|6.4296| 3.6518|\n|7.0708| 5.3893|\n+------+-------+\nonly showing top 20 rows\n\nroot\n |-- Store: double (nullable = true)\n |-- Profit: double (nullable = true)\n\n"
                }
            ], 
            "source": "df1.show()\ndf1.printSchema()"
        }, 
        {
            "execution_count": 5, 
            "cell_type": "code", 
            "metadata": {}, 
            "outputs": [
                {
                    "output_type": "stream", 
                    "name": "stdout", 
                    "text": "+-------+-----------------+\n|summary|           Profit|\n+-------+-----------------+\n|  count|               97|\n|   mean| 5.83913505154639|\n| stddev|5.510262255231546|\n|    min|          -2.6807|\n|    max|           24.147|\n+-------+-----------------+\n\n"
                }
            ], 
            "source": "df1.describe(\"Profit\").show()"
        }, 
        {
            "execution_count": 6, 
            "cell_type": "code", 
            "metadata": {}, 
            "outputs": [
                {
                    "execution_count": 6, 
                    "metadata": {}, 
                    "data": {
                        "text/plain": "StructType(StructField(Store,DoubleType,true), StructField(Profit,DoubleType,true))"
                    }, 
                    "output_type": "execute_result"
                }
            ], 
            "source": "df1.schema"
        }, 
        {
            "execution_count": 22, 
            "cell_type": "code", 
            "metadata": {}, 
            "outputs": [
                {
                    "output_type": "stream", 
                    "name": "stdout", 
                    "text": "+-------+---------+\n|  label|features1|\n+-------+---------+\n| 17.592|   6.1101|\n| 9.1302|   5.5277|\n| 13.662|   8.5186|\n| 11.854|   7.0032|\n| 6.8233|   5.8598|\n| 11.886|   8.3829|\n| 4.3483|   7.4764|\n|   12.0|   8.5781|\n| 6.5987|   6.4862|\n| 3.8166|   5.0546|\n| 3.2522|   5.7107|\n| 15.505|   14.164|\n| 3.1551|    5.734|\n| 7.2258|   8.4084|\n|0.71618|   5.6407|\n| 3.5129|   5.3794|\n| 5.3048|   6.3654|\n|0.56077|   5.1301|\n| 3.6518|   6.4296|\n| 5.3893|   7.0708|\n+-------+---------+\nonly showing top 20 rows\n\n"
                }
            ], 
            "source": "val df2 = df1.select(df1(\"Profit\").as(\"label\"), df1(\"Store\").as(\"features1\"))\ndf2.show()"
        }, 
        {
            "execution_count": 25, 
            "cell_type": "code", 
            "metadata": {
                "collapsed": true
            }, 
            "outputs": [], 
            "source": "import org.apache.spark.ml.regression._\nimport org.apache.spark.ml.feature.VectorAssembler\nval assembler = new VectorAssembler().setInputCols(Array(\"features1\")).setOutputCol(\"features\")"
        }, 
        {
            "execution_count": 26, 
            "cell_type": "code", 
            "metadata": {}, 
            "outputs": [], 
            "source": "val df3 = assembler.transform(df2).select($\"label\",$\"features\")"
        }, 
        {
            "execution_count": 27, 
            "cell_type": "code", 
            "metadata": {}, 
            "outputs": [], 
            "source": "val lr = new LinearRegression()\nval lrModel = lr.fit(df3)"
        }, 
        {
            "execution_count": 28, 
            "cell_type": "code", 
            "metadata": {}, 
            "outputs": [
                {
                    "output_type": "stream", 
                    "name": "stdout", 
                    "text": "Coefficients: [1.1930336441895963] Intercept: -3.895780878311882\nnumIterations: 1\nobjectiveHistory: List(0.0)\n+--------------------+\n|           residuals|\n+--------------------+\n|  14.198226008949028|\n|    6.43124880332505|\n|   7.394804476918388|\n|  7.3947276613233015|\n|  3.7281423300896854|\n|   5.780699142434916|\n| -0.6755158591072163|\n|   5.661818975089107|\n|  2.7562260553693223|\n|  1.6820730203911491|\n| 0.33492364643835426|\n|  2.5026523420104407|\n| 0.21002596252873662|\n|  1.0900767845080797|\n|  -2.117583998468374|\n|  0.9908756927583671|\n|  1.6064445195874253|\n| -1.6638310197451653|\n|-0.12314824036954564|\n|  0.8493785869760853|\n+--------------------+\nonly showing top 20 rows\n\nRMSE: 2.9923139460876023\nMSE: 8.953942751950358\nr2: 0.70203155378414\n"
                }
            ], 
            "source": "println(s\"Coefficients: ${lrModel.coefficients} Intercept: ${lrModel.intercept}\")\n\nval trainingSummary = lrModel.summary\n\nprintln(s\"numIterations: ${trainingSummary.totalIterations}\")\nprintln(s\"objectiveHistory: ${trainingSummary.objectiveHistory.toList}\")\n\ntrainingSummary.residuals.show()\n\nprintln(s\"RMSE: ${trainingSummary.rootMeanSquaredError}\")\nprintln(s\"MSE: ${trainingSummary.meanSquaredError}\")\nprintln(s\"r2: ${trainingSummary.r2}\")"
        }
    ], 
    "metadata": {
        "kernelspec": {
            "display_name": "Scala 2.11 with Spark 2.1", 
            "name": "scala-spark21", 
            "language": "scala"
        }, 
        "language_info": {
            "mimetype": "text/x-scala", 
            "version": "2.11.8", 
            "name": "scala", 
            "pygments_lexer": "scala", 
            "file_extension": ".scala", 
            "codemirror_mode": "text/x-scala"
        }
    }, 
    "nbformat": 4
}