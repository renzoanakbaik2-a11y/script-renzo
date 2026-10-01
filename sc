-- ==========================================================
-- SPEED HUB X - FORCE SKIP GACHA ANIMATION
-- ==========================================================

local Players = game:GetService("Players")
local LocalPlayer = Players.LocalPlayer
local PlayerGui = LocalPlayer:WaitForChild("PlayerGui")
local Workspace = game:GetService("Workspace")

if PlayerGui:FindFirstChild("ForceSkipRollUI") then
    PlayerGui.ForceSkipRollUI:Destroy()
end

-- 1. FUNGSI MENYEMBUNYIKAN / MEMATIKAN GUI ANIMASI GACHA GAME
local function DisableGachaAnimations()
    for _, gui in ipairs(PlayerGui:GetChildren()) do
        if gui:IsA("ScreenGui") and gui.Name ~= "ForceSkipRollUI" then
            local gName = string.lower(gui.Name)
            if string.find(gName, "roll") or string.find(gName, "spin") or string.find(gName, "gacha") or string.find(gName, "cutscene") then
                gui.Enabled = false
            end
        end
    end
end

-- Matikan skrip/efek animasi UI saat ini dan yang akan datang
DisableGachaAnimations()
PlayerGui.ChildAdded:Connect(function(child)
    if child:IsA("ScreenGui") then
        local gName = string.lower(child.Name)
        if string.find(gName, "roll") or string.find(gName, "spin") or string.find(gName, "gacha") or string.find(gName, "cutscene") then
            task.wait()
            child.Enabled = false
        end
    end
end)

-- 2. TAMPILAN UI KONTROL
local ScreenGui = Instance.new("ScreenGui")
ScreenGui.Name = "ForceSkipRollUI"
ScreenGui.ResetOnSpawn = false
ScreenGui.Parent = PlayerGui

local MainFrame = Instance.new("Frame")
MainFrame.Size = UDim2.new(0, 260, 0, 140)
MainFrame.Position = UDim2.new(0.5, -130, 0.4, -70)
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
Title.Text = "No-Animation Auto Roll"
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

local StatusLabel = Instance.new("TextLabel")
StatusLabel.Size = UDim2.new(1, -20, 0, 30)
StatusLabel.Position = UDim2.new(0, 10, 0, 30)
StatusLabel.BackgroundTransparency = 1
StatusLabel.Text = "Status: Fast Roll Ready"
StatusLabel.TextColor3 = Color3.fromRGB(200, 200, 210)
StatusLabel.Font = Enum.Font.SourceSansSemibold
StatusLabel.TextSize = 11
StatusLabel.TextXAlignment = Enum.TextXAlignment.Left
StatusLabel.Parent = MainFrame

local ToggleBtn = Instance.new("TextButton")
ToggleBtn.Size = UDim2.new(1, -20, 0, 32)
ToggleBtn.Position = UDim2.new(0, 10, 0, 68)
ToggleBtn.BackgroundColor3 = Color3.fromRGB(60, 60, 75)
ToggleBtn.Text = "START FAST ROLL"
ToggleBtn.TextColor3 = Color3.fromRGB(255, 255, 255)
ToggleBtn.Font = Enum.Font.SourceSansBold
ToggleBtn.TextSize = 11
ToggleBtn.Parent = MainFrame

local BtnCorner = Instance.new("UICorner")
BtnCorner.CornerRadius = UDim.new(0, 6)
BtnCorner.Parent = ToggleBtn

-- 3. MENCARI PROMPT TERDEKAT
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

-- 4. EKSEKUSI FAST ROLL
local isRolling = false

ToggleBtn.MouseButton1Click:Connect(function()
    isRolling = not isRolling
    if isRolling then
        ToggleBtn.BackgroundColor3 = Color3.fromRGB(255, 30, 60)
        ToggleBtn.Text = "STOP FAST ROLL"
        StatusLabel.Text = "Status: Fast Rolling (No Anim)..."

        task.spawn(function()
            while isRolling do
                DisableGachaAnimations()
                local prompt = GetMyRollPrompt()
                if prompt then
                    if fireproximityprompt then
                        fireproximityprompt(prompt)
                    else
                        prompt:InputHoldBegin()
                        task.wait(0.02)
                        prompt:InputHoldEnd()
                    end
                else
                    StatusLabel.Text = "Status: Dekati tombol merahmu!"
                end
                
                -- Kecepatan Fast Roll (0.25s)
                task.wait(0.25)
            end
        end)
    else
        ToggleBtn.BackgroundColor3 = Color3.fromRGB(60, 60, 75)
        ToggleBtn.Text = "START FAST ROLL"
        StatusLabel.Text = "Status: Off"
    end
end)-- ==========================================================
-- SPEED HUB X - FAST AUTO ROLL (SKIP ANIMATION / INSTANT)
-- ==========================================================

local Players = game:GetService("Players")
local LocalPlayer = Players.LocalPlayer
local PlayerGui = LocalPlayer:WaitForChild("PlayerGui")
local Workspace = game:GetService("Workspace")
local ReplicatedStorage = game:GetService("ReplicatedStorage")

if PlayerGui:FindFirstChild("FastRollUI") then
    PlayerGui.FastRollUI:Destroy()
end

-- 1. BYPASS / DISABLE ANIMASI KAMERA GACHA (ROLLCAMERA)
pcall(function()
    local Net = ReplicatedStorage:WaitForChild("Modules", 2):WaitForChild("Util", 2):WaitForChild("Net", 2)
    local RollCameraRemote = Net:FindFirstChild("RollCamera")
    
    if RollCameraRemote then
        -- Menyadap OnClientEvent agar kamera tidak membesar / menyorot saat gacha
        RollCameraRemote.OnClientEvent:Connect(function(...)
            -- Menonaktifkan pergerakan kamera gacha
            local Camera = Workspace.CurrentCamera
            if Camera then
                Camera.CameraType = Enum.CameraType.Custom
            end
        end)
    end
end)

-- 2. TAMPILAN UI
local ScreenGui = Instance.new("ScreenGui")
ScreenGui.Name = "FastRollUI"
ScreenGui.ResetOnSpawn = false
ScreenGui.Parent = PlayerGui

local MainFrame = Instance.new("Frame")
MainFrame.Size = UDim2.new(0, 260, 0, 150)
MainFrame.Position = UDim2.new(0.5, -130, 0.4, -75)
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
Title.Text = "Instant Roll (Skip Animation)"
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

local StatusLabel = Instance.new("TextLabel")
StatusLabel.Size = UDim2.new(1, -20, 0, 30)
StatusLabel.Position = UDim2.new(0, 10, 0, 30)
StatusLabel.BackgroundTransparency = 1
StatusLabel.Text = "Status: Stand dekat tombol merah!"
StatusLabel.TextColor3 = Color3.fromRGB(200, 200, 210)
StatusLabel.Font = Enum.Font.SourceSansSemibold
StatusLabel.TextSize = 10
StatusLabel.TextXAlignment = Enum.TextXAlignment.Left
StatusLabel.Parent = MainFrame

local ToggleBtn = Instance.new("TextButton")
ToggleBtn.Size = UDim2.new(1, -20, 0, 32)
ToggleBtn.Position = UDim2.new(0, 10, 0, 68)
ToggleBtn.BackgroundColor3 = Color3.fromRGB(60, 60, 75)
ToggleBtn.Text = "START INSTANT ROLL"
ToggleBtn.TextColor3 = Color3.fromRGB(255, 255, 255)
ToggleBtn.Font = Enum.Font.SourceSansBold
ToggleBtn.TextSize = 11
ToggleBtn.Parent = MainFrame

local BtnCorner = Instance.new("UICorner")
BtnCorner.CornerRadius = UDim.new(0, 6)
BtnCorner.Parent = ToggleBtn

local FastToggle = Instance.new("TextButton")
FastToggle.Size = UDim2.new(1, -20, 0, 24)
FastToggle.Position = UDim2.new(0, 10, 0, 108)
FastToggle.BackgroundColor3 = Color3.fromRGB(255, 30, 60)
FastToggle.Text = "Skip Animasi: ON"
FastToggle.TextColor3 = Color3.fromRGB(255, 255, 255)
FastToggle.Font = Enum.Font.SourceSansSemibold
FastToggle.TextSize = 10
FastToggle.Parent = MainFrame

local FastCorner = Instance.new("UICorner")
FastCorner.CornerRadius = UDim.new(0, 4)
FastCorner.Parent = FastToggle

local skipAnimActive = true
FastToggle.MouseButton1Click:Connect(function()
    skipAnimActive = not skipAnimActive
    FastToggle.Text = skipAnimActive and "Skip Animasi: ON" or "Skip Animasi: OFF"
    FastToggle.BackgroundColor3 = skipAnimActive and Color3.fromRGB(255, 30, 60) or Color3.fromRGB(60, 60, 75)
end)

-- 3. LOGIKA MENCARI PROMPT TERDEKAT
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

-- 4. EKSEKUSI AUTO ROLL LETS GO
local isRolling = false

ToggleBtn.MouseButton1Click:Connect(function()
    isRolling = not isRolling
    if isRolling then
        ToggleBtn.BackgroundColor3 = Color3.fromRGB(255, 30, 60)
        ToggleBtn.Text = "STOP INSTANT ROLL"
        StatusLabel.Text = "Status: Instant Rolling..."

        task.spawn(function()
            while isRolling do
                local prompt = GetMyRollPrompt()
                if prompt then
                    -- Matikan gerak kamera saat memicu
                    if skipAnimActive then
                        local Camera = Workspace.CurrentCamera
                        if Camera then
                            Camera.CameraType = Enum.CameraType.Custom
                        end
                    end

                    if fireproximityprompt then
                        fireproximityprompt(prompt)
                    else
                        prompt:InputHoldBegin()
                        task.wait(0.05)
                        prompt:InputHoldEnd()
                    end
                else
                    StatusLabel.Text = "Status: Dekati tombol merahmu!"
                end
                
                -- Kecepatan Roll (Aman & Instan)
                task.wait(skipAnimActive and 0.35 or 0.65)
            end
        end)
    else
        ToggleBtn.BackgroundColor3 = Color3.fromRGB(60, 60, 75)
        ToggleBtn.Text = "START INSTANT ROLL"
        StatusLabel.Text = "Status: Off"
    end
end)-- ==========================================================
-- SPEED HUB X - ANTI-KICK HUMANLIKE AUTO ROLL
-- ==========================================================

local Players = game:GetService("Players")
local LocalPlayer = Players.LocalPlayer
local PlayerGui = LocalPlayer:WaitForChild("PlayerGui")
local Workspace = game:GetService("Workspace")

if PlayerGui:FindFirstChild("SafeRollUI") then
    PlayerGui.SafeRollUI:Destroy()
end

-- 1. TAMPILAN UI
local ScreenGui = Instance.new("ScreenGui")
ScreenGui.Name = "SafeRollUI"
ScreenGui.ResetOnSpawn = false
ScreenGui.Parent = PlayerGui

local MainFrame = Instance.new("Frame")
MainFrame.Size = UDim2.new(0, 260, 0, 140)
MainFrame.Position = UDim2.new(0.5, -130, 0.4, -70)
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
Title.Text = "Safe Auto Roll (Anti-Kick)"
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

local StatusLabel = Instance.new("TextLabel")
StatusLabel.Size = UDim2.new(1, -20, 0, 30)
StatusLabel.Position = UDim2.new(0, 10, 0, 30)
StatusLabel.BackgroundTransparency = 1
StatusLabel.Text = "Status: Stand dekat tombol merah!"
StatusLabel.TextColor3 = Color3.fromRGB(200, 200, 210)
StatusLabel.Font = Enum.Font.SourceSansSemibold
StatusLabel.TextSize = 10
StatusLabel.TextXAlignment = Enum.TextXAlignment.Left
StatusLabel.Parent = MainFrame

local ToggleBtn = Instance.new("TextButton")
ToggleBtn.Size = UDim2.new(1, -20, 0, 32)
ToggleBtn.Position = UDim2.new(0, 10, 0, 68)
ToggleBtn.BackgroundColor3 = Color3.fromRGB(60, 60, 75)
ToggleBtn.Text = "START SAFE AUTO ROLL"
ToggleBtn.TextColor3 = Color3.fromRGB(255, 255, 255)
ToggleBtn.Font = Enum.Font.SourceSansBold
ToggleBtn.TextSize = 11
ToggleBtn.Parent = MainFrame

local BtnCorner = Instance.new("UICorner")
BtnCorner.CornerRadius = UDim.new(0, 6)
BtnCorner.Parent = ToggleBtn

-- 2. LOGIKA MENCARI PROMPT TERDEKAT SECARA NATURAL
local function GetMyRollPrompt()
    local character = LocalPlayer.Character
    if not character or not character:FindFirstChild("HumanoidRootPart") then return nil end
    local myPos = character.HumanoidRootPart.Position

    local closestPrompt = nil
    local shortestDistance = 15 -- Jarak maksimal wajar (15 studs)

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

-- 3. EKSEKUSI DENGAN DELAY AMAN
local isRolling = false

ToggleBtn.MouseButton1Click:Connect(function()
    isRolling = not isRolling
    if isRolling then
        ToggleBtn.BackgroundColor3 = Color3.fromRGB(255, 30, 60)
        ToggleBtn.Text = "STOP AUTO ROLL"
        StatusLabel.Text = "Status: Rolling Aman..."

        task.spawn(function()
            while isRolling do
                local prompt = GetMyRollPrompt()
                if prompt then
                    if fireproximityprompt then
                        fireproximityprompt(prompt)
                    else
                        prompt:InputHoldBegin()
                        task.wait(0.1)
                        prompt:InputHoldEnd()
                    end
                else
                    StatusLabel.Text = "Status: Dekati tombol merahmu!"
                end
                
                -- Cooldown aman manusia (0.6 - 0.7 detik)
                task.wait(0.65)
            end
        end)
    else
        ToggleBtn.BackgroundColor3 = Color3.fromRGB(60, 60, 75)
        ToggleBtn.Text = "START SAFE AUTO ROLL"
        StatusLabel.Text = "Status: Off"
    end
end)
