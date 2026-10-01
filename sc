-- ==========================================================
-- SPEED HUB X - DIRECT MODULE ROLL TESTER
-- ==========================================================

local ReplicatedStorage = game:GetService("ReplicatedStorage")
local Modules = ReplicatedStorage:WaitForChild("Modules")

local success = false

for _, module in ipairs(Modules:GetDescendants()) do
    if module:IsA("ModuleScript") and module.Name == "RandomFish" then
        local status, loadedMod = pcall(require, module)
        if status and type(loadedMod) == "table" and loadedMod.Roll then
            print("--- MEMANGGIL RANDOMFISH.ROLL() ---")
            pcall(function()
                loadedMod.Roll()
            end)
            success = true
        end
    end
end

if not success then
    print("Mencoba modul Rolling...")
    for _, module in ipairs(Modules:GetDescendants()) do
        if module:IsA("ModuleScript") and module.Name == "Rolling" then
            local status, loadedMod = pcall(require, module)
            if status and type(loadedMod) == "table" and loadedMod.Roll then
                print("--- MEMANGGIL ROLLING.ROLL() ---")
                pcall(function()
                    loadedMod.Roll()
                end)
            end
        end
    end
end
