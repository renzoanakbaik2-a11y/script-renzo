-- ==========================================================
-- SPEED HUB X - MODULE FUNCTION DIRECT CALL
-- ==========================================================

local ReplicatedStorage = game:GetService("ReplicatedStorage")
local Players = game:GetService("Players")
local LocalPlayer = Players.LocalPlayer

print("--- MENCOBA EKSKUSI MODUL INTERNAL ---")

local success = false

-- 1. Mencari Modul Net/Util Bawaan Game
pcall(function()
    local Modules = ReplicatedStorage:FindFirstChild("Modules")
    if Modules then
        for _, module in ipairs(Modules:GetDescendants()) do
            if module:IsA("ModuleScript") then
                local modName = string.lower(module.Name)
                if string.find(modName, "net") or string.find(modName, "roll") or string.find(modName, "fish") then
                    local loadedMod = require(module)
                    if type(loadedMod) == "table" then
                        for funcName, func in pairs(loadedMod) do
                            if type(func) == "function" then
                                local fname = string.lower(toBgstring(funcName))
                                if string.find(fname, "roll") or string.find(fname, "buy") then
                                    print("Ditemukan fungsi modul:", funcName)
                                    pcall(function() func() end)
                                    success = true
                                end
                            end
                        end
                    end
                end
            end
        end
    end
end)

if not success then
    print("Modul tidak merespons secara langsung. Memeriksa keberadaan ClickDetector...")
    local rollStands = workspace:FindFirstChild("RollStands") or workspace:FindFirstChild("Buildings")
    if rollStands then
        for _, cd in ipairs(rollStands:GetDescendants()) do
            if cd:IsA("ClickDetector") then
                print("ClickDetector ditemukan pada:", cd:GetFullName())
            end
        end
    end
end-- ==========================================================
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
