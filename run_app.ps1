$proc = Start-Process -FilePath "mvn" -ArgumentList "spring-boot:run" -RedirectStandardOutput "app_run_out.txt" -RedirectStandardError "app_run_err.txt" -PassThru -WindowStyle Hidden
Write-Host "Started PID: $($proc.Id)"
