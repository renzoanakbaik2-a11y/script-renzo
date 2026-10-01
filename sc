-- ==========================================================
-- SPEED HUB X - TRUE LOCK & PAUSE (FIXED)
-- ==========================================================

local Players = game:GetService("Players")
local LocalPlayer = Players.LocalPlayer
local PlayerGui = LocalPlayer:WaitForChild("PlayerGui")
local Workspace = game:GetService("Workspace")

if PlayerGui:FindFirstChild("SpeedHubTrueLockUI") then
    PlayerGui.SpeedHubTrueLockUI:Destroy()
end

-- 1. UI UTAMA
local ScreenGui = Instance.new("ScreenGui")
ScreenGui.Name = "SpeedHubTrueLockUI"
ScreenGui.ResetOnSpawn = false
ScreenGui.Parent = PlayerGui

local MainFrame = Instance.new("Frame")
MainFrame.Size = UDim2.new(0, 320, 0, 280)
MainFrame.Position = UDim2.new(0.5, -160, 0.4, -140)
MainFrame.BackgroundColor3 = Color3.fromRGB(18, 18, 24)
MainFrame.Active = true
MainFrame.Draggable = true
MainFrame.Parent = ScreenGui

local Corner = Instance.new("UICorner")
Corner.CornerRadius = UDim.new(0, 8)
Corner.Parent = MainFrame

local Stroke = Instance.new("UIStroke")
Stroke.Color = Color3.fromRGB(255, 30, 60)
Stroke.Thickness = 2
Stroke.Parent = MainFrame

local Title = Instance.new("TextLabel")
Title.Size = UDim2.new(1, -30, 0, 28)
Title.Position = UDim2.new(0, 10, 0, 0)
Title.BackgroundTransparency = 1
Title.Text = "Speed Hub X | True Lock & Pause"
Title.TextColor3 = Color3.fromRGB(255, 255, 255)
Title.Font = Enum.Font.SourceSansBold
Title.TextSize = 12
Title.TextXAlignment = Enum.TextXAlignment.Left
Title.Parent = MainFrame

local CloseBtn = Instance.new("TextButton")
CloseBtn.Size = UDim2.new(0, 20, 0, 20)
CloseBtn.Position = UDim2.new(1, -24, 0, 4)
CloseBtn.BackgroundColor3 = Color3.fromRGB(255, 30, 60)
CloseBtn.Text = "X"
CloseBtn.TextColor3 = Color3.fromRGB(255, 255, 255)
CloseBtn.Font = Enum.Font.SourceSansBold
CloseBtn.TextSize = 10
CloseBtn.Parent = MainFrame

CloseBtn.MouseButton1Click:Connect(function()
    ScreenGui:Destroy()
end)

-- 2. DAFTAR DROPDOWN NELAYAN
local FishermanList = {
    Common = {"Homeless Fisher", "Lobster Trap", "Rookie Sam", "Deep Fisher"},
    Rare = {"Angler Mia", "Alaskan", "Uncle Bob", "Koi Fisher", "Gnome"},
    Epic = {"Pirate Pete", "Sir Trooper", "Feather Boy", "Dr. Bob", "Pearl Diver", "Miner"},
    Legendary = {"Clown Timmy", "Coral Zoe", "Cloud Nine", "Wizard Tom", "Toad Fisher"},
    Mythical = {"Soldier Steve", "Sea Commander", "Cursed Pirate", "Arctic Noah"},
    Divine = {"Alien Fisher", "Bee Keeper", "Hazmat"},
    Deep = {"Tide Knight", "Necromancer", "Ice King", "Tyrone"},
    Event = {"Tidal Champion", "Kid Floaty", "Crab Lord", "Beach King", "Sea Specialist"}
}

local selectedTargets = {}

local DropFrame = Instance.new("Frame")
DropFrame.Size = UDim2.new(1, -20, 0, 32)
DropFrame.Position = UDim2.new(0, 10, 0, 32)
DropFrame.BackgroundColor3 = Color3.fromRGB(28, 28, 38)
DropFrame.ClipsDescendants = true
DropFrame.Parent = MainFrame

local DropCorner = Instance.new("UICorner")
DropCorner.CornerRadius = UDim.new(0, 6)
DropCorner.Parent = DropFrame

local DropBtn = Instance.new("TextButton")
DropBtn.Size = UDim2.new(1, 0, 0, 32)
DropBtn.BackgroundTransparency = 1
DropBtn.Text = "  [ Pilih Nelayan Target Lock ]"
DropBtn.TextColor3 = Color3.fromRGB(240, 240, 250)
DropBtn.Font = Enum.Font.SourceSansBold
DropBtn.TextSize = 11
DropBtn.TextXAlignment = Enum.TextXAlignment.Left
DropBtn.Parent = DropFrame

local Arrow = Instance.new("TextLabel")
Arrow.Size = UDim2.new(0, 30, 0, 32)
Arrow.Position = UDim2.new(1, -30, 0, 0)
Arrow.BackgroundTransparency = 1
Arrow.Text = "∨"
Arrow.TextColor3 = Color3.fromRGB(255, 30, 60)
Arrow.Font = Enum.Font.SourceSansBold
Arrow.TextSize = 12
Arrow.Parent = DropFrame

local Scroll = Instance.new("ScrollingFrame")
Scroll.Size = UDim2.new(1, -8, 0, 110)
Scroll.Position = UDim2.new(0, 4, 0, 32)
Scroll.BackgroundTransparency = 1
Scroll.BorderSizePixel = 0
Scroll.ScrollBarThickness = 3
Scroll.ScrollBarImageColor3 = Color3.fromRGB(255, 30, 60)
Scroll.Parent = DropFrame

local ScrollLayout = Instance.new("UIListLayout")
ScrollLayout.Padding = UDim.new(0, 3)
ScrollLayout.Parent = Scroll

local isDropOpen = false
DropBtn.MouseButton1Click:Connect(function()
    isDropOpen = not isDropOpen
    DropFrame.Size = isDropOpen and UDim2.new(1, -20, 0, 150) or UDim2.new(1, -20, 0, 32)
    Arrow.Text = isDropOpen and "∧" or "∨"
end)

for rarityName, fishers in pairs(FishermanList) do
    local RarityHeader = Instance.new("TextLabel")
    RarityHeader.Size = UDim2.new(1, 0, 0, 18)
    RarityHeader.BackgroundTransparency = 1
    RarityHeader.Text = "-- " .. string.upper(rarityName) .. " --"
    RarityHeader.TextColor3 = Color3.fromRGB(255, 30, 60)
    RarityHeader.Font = Enum.Font.SourceSansBold
    RarityHeader.TextSize = 10
    RarityHeader.Parent = Scroll

    for _, name in ipairs(fishers) do
        local ItemBtn = Instance.new("TextButton")
        ItemBtn.Size = UDim2.new(1, -6, 0, 24)
        ItemBtn.BackgroundColor3 = Color3.fromRGB(35, 35, 48)
        ItemBtn.Text = "  " .. name
        ItemBtn.TextColor3 = Color3.fromRGB(180, 180, 190)
        ItemBtn.Font = Enum.Font.SourceSansSemibold
        ItemBtn.TextSize = 10
        ItemBtn.TextXAlignment = Enum.TextXAlignment.Left
        ItemBtn.Parent = Scroll

        local ItemCorner = Instance.new("UICorner")
        ItemCorner.CornerRadius = UDim.new(0, 4)
        ItemCorner.Parent = ItemBtn

        local ItemCheck = Instance.new("TextLabel")
        ItemCheck.Size = UDim2.new(0, 20, 1, 0)
        ItemCheck.Position = UDim2.new(1, -22, 0, 0)
        ItemCheck.BackgroundTransparency = 1
        ItemCheck.Text = ""
        ItemCheck.TextColor3 = Color3.fromRGB(255, 30, 60)
        ItemCheck.Font = Enum.Font.SourceSansBold
        ItemCheck.TextSize = 11
        ItemCheck.Parent = ItemBtn

        ItemBtn.MouseButton1Click:Connect(function()
            if selectedTargets[name] then
                selectedTargets[name] = nil
                ItemBtn.BackgroundColor3 = Color3.fromRGB(35, 35, 48)
                ItemBtn.TextColor3 = Color3.fromRGB(180, 180, 190)
                ItemCheck.Text = ""
            else
                selectedTargets[name] = true
                ItemBtn.BackgroundColor3 = Color3.fromRGB(55, 20, 30)
                ItemBtn.TextColor3 = Color3.fromRGB(255, 255, 255)
                ItemCheck.Text = "✓"
            end

            local count = 0
            for _ in pairs(selectedTargets) do count = count + 1 end
            DropBtn.Text = count > 0 and "  [ " .. count .. " Target Locked ]" or "  [ Pilih Nelayan Target Lock ]"
        end)
    end
end
Scroll.CanvasSize = UDim2.new(0, 0, 0, ScrollLayout.AbsoluteContentSize.Y + 10)

local StatusBox = Instance.new("TextLabel")
StatusBox.Size = UDim2.new(1, -20, 0, 38)
StatusBox.Position = UDim2.new(0, 10, 0, 155)
StatusBox.BackgroundColor3 = Color3.fromRGB(12, 12, 16)
StatusBox.TextColor3 = Color3.fromRGB(100, 255, 150)
StatusBox.Font = Enum.Font.Code
StatusBox.TextSize = 10
StatusBox.TextXAlignment = Enum.TextXAlignment.Left
StatusBox.TextYAlignment = Enum.TextYAlignment.Top
StatusBox.Text = "Status: Pilih target & aktifkan sakelar..."
StatusBox.Parent = MainFrame

local BoxCorner = Instance.new("UICorner")
BoxCorner.CornerRadius = UDim.new(0, 6)
BoxCorner.Parent = StatusBox

-- 3. TOMBOL SAKELAR ON/OFF
local ToggleFrame = Instance.new("Frame")
ToggleFrame.Size = UDim2.new(1, -20, 0, 34)
ToggleFrame.Position = UDim2.new(0, 10, 0, 200)
ToggleFrame.BackgroundColor3 = Color3.fromRGB(28, 28, 38)
ToggleFrame.Parent = MainFrame

local ToggleCorner = Instance.new("UICorner")
ToggleCorner.CornerRadius = UDim.new(0, 6)
ToggleCorner.Parent = ToggleFrame

local ToggleLabel = Instance.new("TextLabel")
ToggleLabel.Size = UDim2.new(1, -55, 1, 0)
ToggleLabel.Position = UDim2.new(0, 10, 0, 0)
ToggleLabel.BackgroundTransparency = 1
ToggleLabel.Text = "Auto Roll & Target Lock"
ToggleLabel.TextColor3 = Color3.fromRGB(220, 220, 230)
ToggleLabel.Font = Enum.Font.SourceSansBold
ToggleLabel.TextSize = 11
ToggleLabel.TextXAlignment = Enum.TextXAlignment.Left
ToggleLabel.Parent = ToggleFrame

local SwitchBtn = Instance.new("TextButton")
SwitchBtn.Size = UDim2.new(0, 45, 0, 20)
SwitchBtn.Position = UDim2.new(1, -50, 0.5, -10)
SwitchBtn.BackgroundColor3 = Color3.fromRGB(60, 60, 75)
SwitchBtn.Text = "OFF"
SwitchBtn.TextColor3 = Color3.fromRGB(255, 255, 255)
SwitchBtn.Font = Enum.Font.SourceSansBold
SwitchBtn.TextSize = 10
SwitchBtn.Parent = ToggleFrame

local SwitchCorner = Instance.new("UICorner")
SwitchCorner.CornerRadius = UDim.new(0, 10)
SwitchCorner.Parent = SwitchBtn

-- 4. FUNGSI PROXIMITY PROMPT
local function GetMyRollPrompt()
    local character = LocalPlayer.Character
    if not character or not character:FindFirstChild("HumanoidRootPart") then return nil end
    local myPos = character.HumanoidRootPart.Position

    local closestPrompt = nil
    local shortestDistance = 15

    local scriptable = Workspace:FindFirstChild("Scriptable")
    if scriptable and scriptable:FindFirstChild("Plots") and scriptable.Plots:FindFirstChild("Buildings") then
        for _, building in ipairs(scriptable.Plots.Buildings:GetChildren()) do
            local rollBtn = building:FindFirstChild("RollButton")
            if rollBtn then
                for _, prompt in ipairs(rollBtn:GetDescendants()) do
                    if prompt:IsA("ProximityPrompt") then
                        local part = prompt.Parent
                        if part and part:IsA("BasePart") then
                            local dist = (part.Position - myPos).Magnitude
                            if dist < shortestDistance then
                                shortestDistance = dist
                                closestPrompt = prompt
                            end
                        end
                    end
                end
            end
        end
    end
    return closestPrompt
end

-- 5. FUNGSI CEK STAND PULAU (COCOKKAN NAMA DI FOLDER FISHERMAN DENGAN TARGET)
local function FindMatchedTargetOnStand(targetsTable)
    local scriptable = Workspace:FindFirstChild("Scriptable")
    if scriptable and scriptable:FindFirstChild("Plots") and scriptable.Plots:FindFirstChild("Buildings") then
        for _, building in ipairs(scriptable.Plots.Buildings:GetChildren()) do
            local rollStands = building:FindFirstChild("RollStands")
            if rollStands then
                for _, stand in ipairs(rollStands:GetChildren()) do
                    local reel = stand:FindFirstChild("Reel")
                    if reel and reel:FindFirstChild("Fisherman") then
                        -- Periksa semua isi model di dalam folder Fisherman
                        for _, child in ipairs(reel.Fisherman:GetChildren()) do
                            local objName = string.lower(child.Name)
                            for targetName, _ in pairs(targetsTable) do
                                local cleanTarget = string.lower(targetName)
                                -- Jika nama objek di stand mengandung nama target yang dicentang
                                if string.find(objName, cleanTarget, 1, true) or string.find(cleanTarget, objName, 1, true) then
                                    return true, targetName
                                end
                            end
                        end
                        -- Cek juga atribut internal jika ada
                        local attrName = reel.Fisherman:GetAttribute("FishermanName")
                        if attrName then
                            local attrLower = string.lower(tostring(attrName))
                            for targetName, _ in pairs(targetsTable) do
                                if string.find(attrLower, string.lower(targetName), 1, true) then
                                    return true, targetName
                                end
                            end
                        end
                    end
                end
            end
        end
    end
    return false, ""
end

-- 6. LOGIKA UTAMA SAKELAR ON/OFF
local isRunning = false

SwitchBtn.MouseButton1Click:Connect(function()
    isRunning = not isRunning

    if isRunning then
        SwitchBtn.BackgroundColor3 = Color3.fromRGB(255, 30, 60)
        SwitchBtn.Text = "ON"

        task.spawn(function()
            while isRunning do
                local targetCount = 0
                for _ in pairs(selectedTargets) do targetCount = targetCount + 1 end

                if targetCount == 0 then
                    StatusBox.Text = "Status: Pilih minimal 1 nelayan target di dropdown!"
                    task.wait(1)
                    continue
                end

                -- Cek apakah nelayan target bener-bener ada di stand
                local isMatched, matchedName = FindMatchedTargetOnStand(selectedTargets)

                if isMatched then
                    -- DIAM TOTAL / PAUSE: Berhenti gacha total sampai nelayan dibeli (stand kosong)
                    StatusBox.Text = "Status: [DIAM/PAUSE] Target Dapet: " .. matchedName .. "\nSilakan dibeli dulu, baru lanjut gacha."
                    task.wait(1) -- Berhenti nge-roll selama target masih nongkrong di stand
                else
                    -- LANJUT GACHA OTOMATIS
                    StatusBox.Text = "Status: [ROLLING] Mencari target..."

                    local prompt = GetMyRollPrompt()
                    if prompt then
                        if fireproximityprompt then
                            fireproximityprompt(prompt)
                        else
                            prompt:InputHoldBegin()
                            task.wait(0.05)
                            prompt:InputHoldEnd()
                        end
                    end

                    task.wait(0.7)
                end
            end
        end)
    else
        isRunning = false
        SwitchBtn.BackgroundColor3 = Color3.fromRGB(60, 60, 75)
        SwitchBtn.Text = "OFF"
        StatusBox.Text = "Status: Sakelar dimatikan."
    end
end)
