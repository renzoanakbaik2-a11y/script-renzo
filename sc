-- ==========================================================
-- TEST SCRIPT - STAND MODEL DETECTOR (ISOLATED TEST)
-- ==========================================================

local Players = game:GetService("Players")
local LocalPlayer = Players.LocalPlayer
local PlayerGui = LocalPlayer:WaitForChild("PlayerGui")
local Workspace = game:GetService("Workspace")

if PlayerGui:FindFirstChild("TestLockRollUI") then
    PlayerGui.TestLockRollUI:Destroy()
end

-- 1. UI TES SEDERHANA
local ScreenGui = Instance.new("ScreenGui")
ScreenGui.Name = "TestLockRollUI"
ScreenGui.ResetOnSpawn = false
ScreenGui.Parent = PlayerGui

local MainFrame = Instance.new("Frame")
MainFrame.Size = UDim2.new(0, 280, 0, 180)
MainFrame.Position = UDim2.new(0.5, -140, 0.4, -90)
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
Title.Text = "TEST: Stand Model Lock Roll"
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

local CloseCorner = Instance.new("UICorner")
CloseCorner.CornerRadius = UDim.new(0, 4)
CloseCorner.Parent = CloseBtn

CloseBtn.MouseButton1Click:Connect(function()
    ScreenGui:Destroy()
end)

local TargetInput = Instance.new("TextBox")
TargetInput.Size = UDim2.new(1, -20, 0, 30)
TargetInput.Position = UDim2.new(0, 10, 0, 32)
TargetInput.BackgroundColor3 = Color3.fromRGB(28, 28, 38)
TargetInput.Text = "Homeless Fisher"
TargetInput.PlaceholderText = "Ketik Nama Nelayan Target..."
TargetInput.TextColor3 = Color3.fromRGB(255, 255, 255)
TargetInput.Font = Enum.Font.SourceSansSemibold
TargetInput.TextSize = 11
TargetInput.Parent = MainFrame

local InputCorner = Instance.new("UICorner")
InputCorner.CornerRadius = UDim.new(0, 6)
InputCorner.Parent = TargetInput

local StatusLabel = Instance.new("TextLabel")
StatusLabel.Size = UDim2.new(1, -20, 0, 30)
StatusLabel.Position = UDim2.new(0, 10, 0, 68)
StatusLabel.BackgroundTransparency = 1
StatusLabel.Text = "Stand Terdeteksi: Scanning..."
StatusLabel.TextColor3 = Color3.fromRGB(200, 200, 210)
StatusLabel.Font = Enum.Font.SourceSansSemibold
StatusLabel.TextSize = 10
StatusLabel.TextXAlignment = Enum.TextXAlignment.Left
StatusLabel.Parent = MainFrame

local ToggleBtn = Instance.new("TextButton")
ToggleBtn.Size = UDim2.new(1, -20, 0, 32)
ToggleBtn.Position = UDim2.new(0, 10, 0, 108)
ToggleBtn.BackgroundColor3 = Color3.fromRGB(60, 60, 75)
ToggleBtn.Text = "START TEST AUTO ROLL"
ToggleBtn.TextColor3 = Color3.fromRGB(255, 255, 255)
ToggleBtn.Font = Enum.Font.SourceSansBold
ToggleBtn.TextSize = 11
ToggleBtn.Parent = MainFrame

local BtnCorner = Instance.new("UICorner")
BtnCorner.CornerRadius = UDim.new(0, 6)
BtnCorner.Parent = ToggleBtn

-- 2. FUNGSI MEMINDAI PROMPT & STAND
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

local function GetStandFishermanNames()
    local foundNames = {}
    local scriptable = Workspace:FindFirstChild("Scriptable")
    if scriptable and scriptable:FindFirstChild("Plots") and scriptable.Plots:FindFirstChild("Buildings") then
        for _, building in ipairs(scriptable.Plots.Buildings:GetChildren()) do
            local rollStands = building:FindFirstChild("RollStands")
            if rollStands then
                for _, stand in ipairs(rollStands:GetChildren()) do
                    local reel = stand:FindFirstChild("Reel")
                    if reel then
                        local fisher = reel:FindFirstChild("Fisherman")
                        if fisher then
                            for _, child in ipairs(fisher:GetChildren()) do
                                table.insert(foundNames, string.lower(child.Name))
                            end
                            if fisher:GetAttribute("FishermanName") then
                                table.insert(foundNames, string.lower(tostring(fisher:GetAttribute("FishermanName"))))
                            end
                        end
                    end
                end
            end
        end
    end
    return foundNames
end

-- 3. LOGIKA TES AUTO ROLL & LOCK
local isRolling = false

ToggleBtn.MouseButton1Click:Connect(function()
    isRolling = not isRolling
    if isRolling then
        ToggleBtn.BackgroundColor3 = Color3.fromRGB(255, 30, 60)
        ToggleBtn.Text = "STOP TEST ROLL"

        task.spawn(function()
            while isRolling do
                local targetText = string.lower(string.gsub(TargetInput.Text, "^%s*(.-)%s*$", "%1"))
                local currentNames = GetStandFishermanNames()
                
                local matched = false
                local detectedName = "None"

                if #currentNames > 0 then
                    detectedName = currentNames[1]
                    for _, name in ipairs(currentNames) do
                        if string.find(name, targetText, 1, true) or string.find(targetText, name, 1, true) then
                            matched = true
                            break
                        end
                    end
                end

                StatusLabel.Text = "Stand: " .. detectedName

                if matched then
                    isRolling = false
                    ToggleBtn.BackgroundColor3 = Color3.fromRGB(60, 60, 75)
                    ToggleBtn.Text = "START TEST AUTO ROLL"
                    StatusLabel.Text = "MATCH FOUND! STOPPED: " .. detectedName
                    break
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

                task.wait(0.7)
            end
        end)
    else
        ToggleBtn.BackgroundColor3 = Color3.fromRGB(60, 60, 75)
        ToggleBtn.Text = "START TEST AUTO ROLL"
        StatusLabel.Text = "Status: Off"
    end
end)-- ==========================================================
-- SPEED HUB X - STAND TEXT DETECTOR (INSPECTOR)
-- ==========================================================

local Players = game:GetService("Players")
local LocalPlayer = Players.LocalPlayer
local PlayerGui = LocalPlayer:WaitForChild("PlayerGui")
local Workspace = game:GetService("Workspace")

if PlayerGui:FindFirstChild("TextDetectorUI") then
    PlayerGui.TextDetectorUI:Destroy()
end

local ScreenGui = Instance.new("ScreenGui")
ScreenGui.Name = "TextDetectorUI"
ScreenGui.ResetOnSpawn = false
ScreenGui.Parent = PlayerGui

local MainFrame = Instance.new("Frame")
MainFrame.Size = UDim2.new(0, 320, 0, 200)
MainFrame.Position = UDim2.new(0.5, -160, 0.4, -100)
MainFrame.BackgroundColor3 = Color3.fromRGB(18, 18, 24)
MainFrame.Active = true
MainFrame.Draggable = true
MainFrame.Parent = ScreenGui

local Corner = Instance.new("UICorner")
Corner.CornerRadius = UDim.new(0, 8)
Corner.Parent = MainFrame

local Title = Instance.new("TextLabel")
Title.Size = UDim2.new(1, -30, 0, 28)
Title.Position = UDim2.new(0, 10, 0, 0)
Title.BackgroundTransparency = 1
Title.Text = "Stand Text Inspector (Deteksi Papan Nama)"
Title.TextColor3 = Color3.fromRGB(255, 255, 255)
Title.Font = Enum.Font.SourceSansBold
Title.TextSize = 11
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

local Scroll = Instance.new("ScrollingFrame")
Scroll.Size = UDim2.new(1, -16, 1, -38)
Scroll.Position = UDim2.new(0, 8, 0, 32)
Scroll.BackgroundColor3 = Color3.fromRGB(12, 12, 16)
Scroll.BorderSizePixel = 0
Scroll.ScrollBarThickness = 3
Scroll.ScrollBarImageColor3 = Color3.fromRGB(255, 30, 60)
Scroll.Parent = MainFrame

local Layout = Instance.new("UIListLayout")
Layout.Padding = UDim.new(0, 4)
Layout.SortOrder = Enum.SortOrder.LayoutOrder
Layout.Parent = Scroll

local function AddTextLog(path, textValue)
    local Label = Instance.new("TextLabel")
    Label.Size = UDim2.new(1, -8, 0, 26)
    Label.BackgroundTransparency = 1
    Label.Text = "• " .. textValue .. "\n  [" .. path .. "]"
    Label.TextColor3 = Color3.fromRGB(255, 100, 120)
    Label.Font = Enum.Font.SourceSansSemibold
    Label.TextSize = 10
    Label.TextXAlignment = Enum.TextXAlignment.Left
    Label.Parent = Scroll
end

-- PEMINDAIAN TEKS DI PLOT PULAU
local scriptable = Workspace:FindFirstChild("Scriptable")
local count = 0

if scriptable and scriptable:FindFirstChild("Plots") and scriptable.Plots:FindFirstChild("Buildings") then
    for _, building in ipairs(scriptable.Plots.Buildings:GetChildren()) do
        for _, desc in ipairs(building:GetDescendants()) do
            if desc:IsA("TextLabel") and desc.Text ~= "" then
                count = count + 1
                AddTextLog(desc.Parent.Name, desc.Text)
            end
        end
    end
end

if count == 0 then
    AddTextLog("Workspace", "Tidak ada TextLabel terdeteksi di area Buildings.")
end

Scroll.CanvasSize = UDim2.new(0, 0, 0, Layout.AbsoluteContentSize.Y + 10)
