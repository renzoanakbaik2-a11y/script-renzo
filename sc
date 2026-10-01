-- ==========================================================
-- TEST SCRIPT: SAKELAR ON/OFF AUTO STOP WATCHER
-- ==========================================================

local Players = game:GetService("Players")
local LocalPlayer = Players.LocalPlayer
local PlayerGui = LocalPlayer:WaitForChild("PlayerGui")
local Workspace = game:GetService("Workspace")

if PlayerGui:FindFirstChild("TestOnOffWatcherUI") then
    PlayerGui.TestOnOffWatcherUI:Destroy()
end

-- 1. TAMPILAN UTAMA UI
local ScreenGui = Instance.new("ScreenGui")
ScreenGui.Name = "TestOnOffWatcherUI"
ScreenGui.ResetOnSpawn = false
ScreenGui.Parent = PlayerGui

local MainFrame = Instance.new("Frame")
MainFrame.Size = UDim2.new(0, 300, 0, 240)
MainFrame.Position = UDim2.new(0.5, -150, 0.4, -120)
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
Title.Text = "TEST: Sakelar ON/OFF Auto Stop"
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

-- 2. DAFTAR DROPDOWN 36 NELAYAN
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
DropBtn.Text = "  [ Pilih Nelayan Target ]"
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
            DropBtn.Text = count > 0 and "  [ " .. count .. " Target Locked ]" or "  [ Pilih Nelayan Target ]"
        end)
    end
end
Scroll.CanvasSize = UDim2.new(0, 0, 0, ScrollLayout.AbsoluteContentSize.Y + 10)

local StatusBox = Instance.new("TextLabel")
StatusBox.Size = UDim2.new(1, -20, 0, 36)
StatusBox.Position = UDim2.new(0, 10, 0, 155)
StatusBox.BackgroundColor3 = Color3.fromRGB(12, 12, 16)
StatusBox.TextColor3 = Color3.fromRGB(100, 255, 150)
StatusBox.Font = Enum.Font.Code
StatusBox.TextSize = 10
StatusBox.TextXAlignment = Enum.TextXAlignment.Left
StatusBox.TextYAlignment = Enum.TextYAlignment.Top
StatusBox.Text = "Status: Pilih target & geser sakelar ke ON..."
StatusBox.Parent = MainFrame

local BoxCorner = Instance.new("UICorner")
BoxCorner.CornerRadius = UDim.new(0, 6)
BoxCorner.Parent = StatusBox

-- 3. TOMBOL SAKELAR ON/OFF
local ToggleFrame = Instance.new("Frame")
ToggleFrame.Size = UDim2.new(1, -20, 0, 32)
ToggleFrame.Position = UDim2.new(0, 10, 0, 198)
ToggleFrame.BackgroundColor3 = Color3.fromRGB(28, 28, 38)
ToggleFrame.Parent = MainFrame

local ToggleCorner = Instance.new("UICorner")
ToggleCorner.CornerRadius = UDim.new(0, 6)
ToggleCorner.Parent = ToggleFrame

local ToggleLabel = Instance.new("TextLabel")
ToggleLabel.Size = UDim2.new(1, -55, 1, 0)
ToggleLabel.Position = UDim2.new(0, 10, 0, 0)
ToggleLabel.BackgroundTransparency = 1
ToggleLabel.Text = "Auto Roll & Stop Sakelar"
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

local isAutoRunning = false

SwitchBtn.MouseButton1Click:Connect(function()
    isAutoRunning = not isAutoRunning

    if isAutoRunning then
        SwitchBtn.BackgroundColor3 = Color3.fromRGB(255, 30, 60)
        SwitchBtn.Text = "ON"

        task.spawn(function()
            while isAutoRunning do
                local targetCount = 0
                for _ in pairs(selectedTargets) do targetCount = targetCount + 1 end
                
                if targetCount == 0 then
                    StatusBox.Text = "Status: Pilih minimal 1 nelayan target dulu!"
                    task.wait(1)
                    continue
                end

                -- Tekan Proximity Prompt Roll
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

                StatusBox.Text = "Status: AFK Running... Memindai layar..."

                -- Pantau layar PlayerGui selama jeda roll
                local startTime = tick()
                local matchedFound = false
                local matchedName = ""

                while tick() - startTime < 0.75 and isAutoRunning do
                    for _, gui in ipairs(PlayerGui:GetChildren()) do
                        if gui:IsA("ScreenGui") and gui.Name ~= "TestOnOffWatcherUI" then
                            for _, desc in ipairs(gui:GetDescendants()) do
                                if (desc:IsA("TextLabel") or desc:IsA("TextButton")) and desc.Visible then
                                    local txt = string.lower(desc.Text)
                                    if txt ~= "" then
                                        for targetName, _ in pairs(selectedTargets) do
                                            local cleanTarget = string.lower(targetName)
                                            if string.find(txt, cleanTarget, 1, true) then
                                                matchedFound = true
                                                matchedName = targetName
                                                break
                                            end
                                        end
                                    end
                                end
                            end
                        end
                        if matchedFound then break end
                    end

                    if matchedFound then break end
                    task.wait(0.05)
                end

                -- Kalau dapet target, sakelar otomatis OFF / STOP!
                if matchedFound then
                    isAutoRunning = false
                    SwitchBtn.BackgroundColor3 = Color3.fromRGB(60, 60, 75)
                    SwitchBtn.Text = "OFF"
                    StatusBox.Text = "BERHASIL DAPET! TARGET: " .. matchedName .. " (AUTO OFF)"
                    break
                end

                task.wait(0.1)
            end
        end)
    else
        isAutoRunning = false
        SwitchBtn.BackgroundColor3 = Color3.fromRGB(60, 60, 75)
        SwitchBtn.Text = "OFF"
        StatusBox.Text = "Status: Sakelar dimatikan manual."
    end
end)-- ==========================================================
-- TEST SCRIPT: DROPDOWN UI STATE WATCHER (STANDALONE)
-- ==========================================================

local Players = game:GetService("Players")
local LocalPlayer = Players.LocalPlayer
local PlayerGui = LocalPlayer:WaitForChild("PlayerGui")
local Workspace = game:GetService("Workspace")

if PlayerGui:FindFirstChild("TestDropdownWatcherUI") then
    PlayerGui.TestDropdownWatcherUI:Destroy()
end

-- 1. TAMPILAN UTAMA UI
local ScreenGui = Instance.new("ScreenGui")
ScreenGui.Name = "TestDropdownWatcherUI"
ScreenGui.ResetOnSpawn = false
ScreenGui.Parent = PlayerGui

local MainFrame = Instance.new("Frame")
MainFrame.Size = UDim2.new(0, 300, 0, 240)
MainFrame.Position = UDim2.new(0.5, -150, 0.4, -120)
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
Title.Text = "TEST: Dropdown UI Watcher"
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

-- 2. DAFTAR DROPDOWN 36 NELAYAN LENGKAP
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
DropBtn.Text = "  [ Pilih Nelayan Target ]"
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
            DropBtn.Text = count > 0 and "  [ " .. count .. " Target Locked ]" or "  [ Pilih Nelayan Target ]"
        end)
    end
end
Scroll.CanvasSize = UDim2.new(0, 0, 0, ScrollLayout.AbsoluteContentSize.Y + 10)

local StatusBox = Instance.new("TextLabel")
StatusBox.Size = UDim2.new(1, -20, 0, 36)
StatusBox.Position = UDim2.new(0, 10, 0, 155)
StatusBox.BackgroundColor3 = Color3.fromRGB(12, 12, 16)
StatusBox.TextColor3 = Color3.fromRGB(100, 255, 150)
StatusBox.Font = Enum.Font.Code
StatusBox.TextSize = 10
StatusBox.TextXAlignment = Enum.TextXAlignment.Left
StatusBox.TextYAlignment = Enum.TextYAlignment.Top
StatusBox.Text = "Status: Pilih nelayan di dropdown..."
StatusBox.Parent = MainFrame

local BoxCorner = Instance.new("UICorner")
BoxCorner.CornerRadius = UDim.new(0, 6)
BoxCorner.Parent = StatusBox

local ToggleBtn = Instance.new("TextButton")
ToggleBtn.Size = UDim2.new(1, -20, 0, 32)
ToggleBtn.Position = UDim2.new(0, 10, 0, 198)
ToggleBtn.BackgroundColor3 = Color3.fromRGB(60, 60, 75)
ToggleBtn.Text = "START TEST ROLL & WATCH"
ToggleBtn.TextColor3 = Color3.fromRGB(255, 255, 255)
ToggleBtn.Font = Enum.Font.SourceSansBold
ToggleBtn.TextSize = 11
ToggleBtn.Parent = MainFrame

local BtnCorner = Instance.new("UICorner")
BtnCorner.CornerRadius = UDim.new(0, 6)
BtnCorner.Parent = ToggleBtn

-- 3. FUNGSI PROXIMITY PROMPT
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

local isRunning = false

ToggleBtn.MouseButton1Click:Connect(function()
    isRunning = not isRunning
    if isRunning then
        ToggleBtn.BackgroundColor3 = Color3.fromRGB(255, 30, 60)
        ToggleBtn.Text = "STOP TEST"

        task.spawn(function()
            while isRunning do
                local targetCount = 0
                for _ in pairs(selectedTargets) do targetCount = targetCount + 1 end
                if targetCount == 0 then
                    StatusBox.Text = "Status: Pilih minimal 1 nelayan target!"
                    task.wait(1)
                    continue
                end

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

                StatusBox.Text = "Status: Meroll... Memindai UI..."

                local startTime = tick()
                local matchedFound = false
                local matchedName = ""

                while tick() - startTime < 0.75 and isRunning do
                    for _, gui in ipairs(PlayerGui:GetChildren()) do
                        if gui:IsA("ScreenGui") and gui.Name ~= "TestDropdownWatcherUI" then
                            for _, desc in ipairs(gui:GetDescendants()) do
                                if (desc:IsA("TextLabel") or desc:IsA("TextButton")) and desc.Visible then
                                    local txt = string.lower(desc.Text)
                                    if txt ~= "" then
                                        for targetName, _ in pairs(selectedTargets) do
                                            local cleanTarget = string.lower(targetName)
                                            if string.find(txt, cleanTarget, 1, true) then
                                                matchedFound = true
                                                matchedName = targetName
                                                break
                                            end
                                        end
                                    end
                                end
                            end
                        end
                        if matchedFound then break end
                    end

                    if matchedFound then break end
                    task.wait(0.05)
                end

                if matchedFound then
                    isRunning = false
                    ToggleBtn.BackgroundColor3 = Color3.fromRGB(60, 60, 75)
                    ToggleBtn.Text = "START TEST ROLL & WATCH"
                    StatusBox.Text = "BERHASIL STOP! TARGET: " .. matchedName
                    break
                end

                task.wait(0.1)
            end
        end)
    else
        ToggleBtn.BackgroundColor3 = Color3.fromRGB(60, 60, 75)
        ToggleBtn.Text = "START TEST ROLL & WATCH"
        StatusBox.Text = "Status: Berhenti."
    end
end)
