for /f "delims=" %%f in ('dir vlfsoft.*.app /b /ad') do set CurrAppName=%%f
call gradlew -q %CurrAppName%:dependencies > %CurrAppName%_dependencies_report.log
pause
