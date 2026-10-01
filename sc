-- ==========================================================
-- SPEED HUB X - DIRECT REMOTE ROLL DIAGNOSTIC
-- ==========================================================

local ReplicatedStorage = game:GetService("ReplicatedStorage")
local Net = ReplicatedStorage:WaitForChild("Modules"):WaitForChild("Util"):WaitForChild("Net")
local BuyRemote = Net:WaitForChild("BuyRandomFish")

print("--- MENCOBA PANGGILAN ROLL ---")

-- Uji coba 3 metode parameter umum
task.spawn(function()
    pcall(function()
        if BuyRemote:IsA("RemoteFunction") then
            print("Mencoba InvokeServer tanpa param:", BuyRemote:InvokeServer())
            print("Mencoba InvokeServer param 1:", BuyRemote:InvokeServer(1))
            print("Mencoba InvokeServer param Stand:", BuyRemote:InvokeServer("RollStand"))
        else
            BuyRemote:FireServer()
            BuyRemote:FireServer(1)
            BuyRemote:FireServer("RollStand")
            print("Sent FireServer calls")
        end
    end)
end)
