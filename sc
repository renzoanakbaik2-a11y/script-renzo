-- ==========================================================
-- TEST SCRIPT V3 - INVENTORY & GUI TEXT LOCK ROLL
-- ==========================================================

local Players = game:GetService("Players")
local LocalPlayer = Players.LocalPlayer
local PlayerGui = LocalPlayer:WaitForChild("PlayerGui")
local Workspace = game:GetService("Workspace")

if PlayerGui:FindFirstChild("TestGuiLockRollUI") then
    PlayerGui.TestGuiLockRollUI:Destroy()
end

-- 1. TAMPILAN UI TES
local ScreenGui = Instance.new("ScreenGui")
ScreenGui.Name = "TestGuiLockRollUI"
ScreenGui.ResetOnSpawn = false
ScreenGui.Parent = PlayerGui

local MainFrame = Instance.new("Frame")
MainFrame.Size = UDim2.new(0, 280, 0, 210)
MainFrame.Position = UDim2.new(0.5, -140, 0.4, -105)
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
Title.Text = "TEST V3: Inventory/GUI Lock Roll"
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

-- 2. DROPDOWN PILIHAN NELAYAN
local FishermanList = {
    Common = {"Homeless Fisher", "Lobster Trap", "Rookie Sam", "Deep Fisher"},
    Rare = {"Angler Mia", "Alaskan", "Uncle Bob", "Koi Fisher", "Gnome"},
    Epic = {"Pirate Pete", "Sir Trooper", "Feather Boy", "Dr. Bob", "Pearl Diver", "Miner"},
    Legendary = {"Clown Timmy", "Coral Zoe", "Cloud Nine", "Wizard Tom", "Toad Fisher"}
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
DropBtn.Text = "  [ Pilih Nelayan Lock ]"
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
Scroll.Size = UDim2.new(1, -8, 0, 90)
Scroll.Position = UDim2.new(0, 4, 0, 32)
Scroll.BackgroundTransparency = 1
Scroll.BorderSizePixel = 0
Scroll.ScrollBarThickness = 3
Scroll.ScrollBarImageColor3 = Color3.fromRGB(255, 30, 60)
Scroll.Parent = DropFrame

local ScrollLayout = Instance.new("UIListLayout")
ScrollLayout.Padding = UDim.new(0, 2)
ScrollLayout.Parent = Scroll

local isDropOpen = false
DropBtn.MouseButton1Click:Connect(function()
    isDropOpen = not isDropOpen
    DropFrame.Size = isDropOpen and UDim2.new(1, -20, 0, 125) or UDim2.new(1, -20, 0, 32)
    Arrow.Text = isDropOpen and "∧" or "∨"
end)

for category, list in pairs(FishermanList) do
    for _, name in ipairs(list) do
        local ItemBtn = Instance.new("TextButton")
        ItemBtn.Size = UDim2.new(1, -6, 0, 22)
        ItemBtn.BackgroundColor3 = Color3.fromRGB(38, 38, 50)
        ItemBtn.Text = "  " .. name
        ItemBtn.TextColor3 = Color3.fromRGB(190, 190, 200)
        ItemBtn.Font = Enum.Font.SourceSansSemibold
        ItemBtn.TextSize = 10
        ItemBtn.TextXAlignment = Enum.TextXAlignment.Left
        ItemBtn.Parent = Scroll

        local ItemCorner = Instance.new("UICorner")
        ItemCorner.CornerRadius = UDim.new(0, 4)
        ItemCorner.Parent = ItemBtn

        ItemBtn.MouseButton1Click:Connect(function()
            if selectedTargets[name] then
                selectedTargets[name] = nil
                ItemBtn.BackgroundColor3 = Color3.fromRGB(38, 38, 50)
                ItemBtn.TextColor3 = Color3.fromRGB(190, 190, 200)
            else
                selectedTargets[name] = true
                ItemBtn.BackgroundColor3 = Color3.fromRGB(255, 30, 60)
                ItemBtn.TextColor3 = Color3.fromRGB(255, 255, 255)
            end

            local count = 0
            for _ in pairs(selectedTargets) do count = count + 1 end
            DropBtn.Text = count > 0 and "  [ " .. count .. " Locked ]" or "  [ Pilih Nelayan Lock ]"
        end)
    end
end
Scroll.CanvasSize = UDim2.new(0, 0, 0, ScrollLayout.AbsoluteContentSize.Y + 10)

local StatusLabel = Instance.new("TextLabel")
StatusLabel.Size = UDim2.new(1, -20, 0, 26)
StatusLabel.Position = UDim2.new(0, 10, 0, 138)
StatusLabel.BackgroundTransparency = 1
StatusLabel.Text = "Status: Siap Tes GUI Scan..."
StatusLabel.TextColor3 = Color3.fromRGB(200, 200, 210)
StatusLabel.Font = Enum.Font.SourceSansSemibold
StatusLabel.TextSize = 10
StatusLabel.TextXAlignment = Enum.TextXAlignment.Left
StatusLabel.Parent = MainFrame

local ToggleBtn = Instance.new("TextButton")
ToggleBtn.Size = UDim2.new(1, -20, 0, 32)
ToggleBtn.Position = UDim2.new(0, 10, 0, 168)
ToggleBtn.BackgroundColor3 = Color3.fromRGB(60, 60, 75)
ToggleBtn.Text = "START TEST ROLL"
ToggleBtn.TextColor3 = Color3.fromRGB(255, 255, 255)
ToggleBtn.Font = Enum.Font.SourceSansBold
ToggleBtn.TextSize = 11
ToggleBtn.Parent = MainFrame

local BtnCorner = Instance.new("UICorner")
BtnCorner.CornerRadius = UDim.new(0, 6)
BtnCorner.Parent = ToggleBtn

-- 3. PROXIMITY PROMPT & PLAYERGUI SCANNER
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

-- MEMINDAI SELURUH TEKS DI PLAYERGUI
local function ScanPlayerGuiTexts()
    local texts = {}
    for _, gui in ipairs(PlayerGui:GetChildren()) do
        if gui:IsA("ScreenGui") and gui.Name ~= "TestGuiLockRollUI" then
            for _, desc in ipairs(gui:GetDescendants()) do
                if desc:IsA("TextLabel") or desc:IsA("TextButton") then
                    if desc.Text and desc.Text ~= "" then
                        table.insert(texts, string.lower(desc.Text))
                    end
                end
            end
        end
    end
    return texts
end

-- 4. EKSEKUSI TES AUTO ROLL DENGAN GUI DETECTOR
local isRolling = false

ToggleBtn.MouseButton1Click:Connect(function()
    isRolling = not isRolling
    if isRolling then
        ToggleBtn.BackgroundColor3 = Color3.fromRGB(255, 30, 60)
        ToggleBtn.Text = "STOP TEST ROLL"

        task.spawn(function()
            while isRolling do
                -- Pemicu Roll
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

                -- Beri waktu gacha berputar & UI memperbarui teks
                task.wait(0.8)

                -- Pindai seluruh UI di layar
                local currentGuiTexts = ScanPlayerGuiTexts()
                local isMatched = false
                local matchedName = ""

                for targetName, _ in pairs(selectedTargets) do
                    local cleanTarget = string.lower(targetName)
                    for _, guiText in ipairs(currentGuiTexts) do
                        if string.find(guiText, cleanTarget, 1, true) then
                            isMatched = true
                            matchedName = targetName
                            break
                        end
                    end
                    if isMatched then break end
                end

                if isMatched then
                    isRolling = false
                    ToggleBtn.BackgroundColor3 = Color3.fromRGB(60, 60, 75)
                    ToggleBtn.Text = "START TEST ROLL"
                    StatusLabel.Text = "GUI MATCH DETECTED! STOPPED: " .. matchedName
                    break
                end

                StatusLabel.Text = "Status: Scanning UI & Rolling..."
            end
        end)
    else
        ToggleBtn.BackgroundColor3 = Color3.fromRGB(60, 60, 75)
        ToggleBtn.Text = "START TEST ROLL"
        StatusLabel.Text = "Status: Off"
    end
end)
